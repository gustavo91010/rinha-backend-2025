package ajudaqui.rinha_de_backend_2025.service

import ajudaqui.rinha_de_backend_2025.client.PaymentPocessor
import ajudaqui.rinha_de_backend_2025.dto.PaymentDto
import ajudaqui.rinha_de_backend_2025.dto.PaymentTask
import ajudaqui.rinha_de_backend_2025.dto.SummaryDto
import ajudaqui.rinha_de_backend_2025.entity.Payments
import ajudaqui.rinha_de_backend_2025.repository.PaymentsRepository
import java.time.Instant
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.reactor.awaitSingle
import org.slf4j.LoggerFactory
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.stereotype.Service

@Service
class PaymentsService(
        private val repository: PaymentsRepository,
        private val paymentProcessors: PaymentPocessor,
        private val redisTemplate: ReactiveRedisTemplate<String, Payments>,
) {
  val logger = LoggerFactory.getLogger(javaClass)

  private val channel = Channel<PaymentTask>(Channel.UNLIMITED)

  suspend fun register(dto: PaymentDto): Map<String, String> =
          mapOf("message" to "pagamento recebido").also {
            channel.send(PaymentTask(dto, Instant.now()))
          }

  fun startProcessing() {
    repeat(2) {
      CoroutineScope(Dispatchers.IO).launch {
        for (task in channel) {
          callProcessor(task.dto, task.time)
        }
      }
    }
  }

  suspend private fun callProcessor(dto: PaymentDto, time: Instant) {
    val selector =
            when {
              trySendPaymentProcessors(dto, true) -> true
              trySendPaymentProcessors(dto, false) -> false
              else -> null
            }
    if (selector != null) {
      savePayments(selector, dto, time)
    } else {
      channel.send(PaymentTask(dto, time))
    }
  }

  private suspend fun trySendPaymentProcessors(dto: PaymentDto, selector: Boolean): Boolean {
    return try {
      paymentProcessors.postPayment(dto, selector).awaitSingle()
    } catch (e: Exception) {
      false
    }
  }

  private suspend fun savePayments(
          default: Boolean,
          paymentDto: PaymentDto,
          requestedAt: Instant? = Instant.now()
  ): Payments =
          repository.save(
                  Payments(
                          correlationId = paymentDto.correlationId,
                          amount = paymentDto.amount,
                          requestedAt = requestedAt,
                          default = default
                  )
          )

  suspend fun summary(from: Instant, to: Instant): SummaryDto {
    var periood = repository.findByPeriod(from, to)
    val periodDefault = periood.filter { it.default }
    val periodFallback = periood.filter { !it.default }
    var default =
            mapOf(
                    "totalRequests" to "${periodDefault.size}",
                    "totalAmount" to "${periodDefault.sumOf { it.amount }}"
            )
    var fallback =
            mapOf(
                    "totalRequests" to "${periodFallback.size}",
                    "totalAmount" to "${periodFallback.sumOf { it.amount }}"
            )
    // Map<String, String> lala= mutableMap()
    return SummaryDto(default, fallback)
  }
}

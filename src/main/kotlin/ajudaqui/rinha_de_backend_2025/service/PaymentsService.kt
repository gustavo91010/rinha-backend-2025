package ajudaqui.rinha_de_backend_2025.service

import ajudaqui.rinha_de_backend_2025.client.PaymentPocessor
import ajudaqui.rinha_de_backend_2025.dto.PaymentDto
import ajudaqui.rinha_de_backend_2025.dto.SummaryDto
import ajudaqui.rinha_de_backend_2025.entity.Payments
import ajudaqui.rinha_de_backend_2025.repository.PaymentsRepository
import java.time.Instant
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
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

  private val channel = Channel<PaymentDto>(Channel.UNLIMITED)
  suspend fun recivedTest(default: Boolean, paymentDto: PaymentDto): Payments =
          savePayments(default, paymentDto)

  suspend fun recived(dto: PaymentDto): Map<String, String> =
          mapOf("message" to "pagamento recebido").also { channel.send(dto) }

  private suspend fun savePayments(default: Boolean, paymentDto: PaymentDto): Payments =
          repository.save(
                          Payments(
                                  correlationId = paymentDto.correlationId,
                                  amount = paymentDto.amount,
                                  default = default
                          )
                  )
                  .also {
                    logger.info(
                            "Pagamento id: {} registrado na rota: {}",
                            paymentDto.correlationId,
                            if (default) "default" else "fallback"
                    )
                  }

  fun startProcessing() {
    CoroutineScope(Dispatchers.IO).launch {
      for (dto in channel) {
        val selector =
                when {
                  trySendPaymentProcessors(dto, true) -> true
                  trySendPaymentProcessors(dto, false) -> false
                  else -> null
                }
        if (selector == null) {
          delay(500)
          channel.send(dto)
        } else {
          savePayments(selector, dto)
        }
        // val sent = trySendPaymentProcessors(dto, true) || trySendPaymentProcessors(dto, false)
        // if (!sent) {
        //   delay(500)
        //   channel.send(dto)
        // }
      }
    }
  }

  private suspend fun trySendPaymentProcessors(dto: PaymentDto, selector: Boolean): Boolean {
    return try {
      val response = paymentProcessors.postPayment(dto, selector).awaitSingle()
      // if (response) savePayments(selector, dto)
      response
    } catch (e: Exception) {
      false
    }
  }

  suspend fun findById(paymentId: String): Payments? =
          repository.findById(paymentId) ?: throw ClassNotFoundException("Pagamento não localizado")

  suspend fun findByPeriod(from: Instant, to: Instant): List<Payments> =
          repository.findByPeriod(from, to)

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
    return SummaryDto(default, fallback)
  }
}

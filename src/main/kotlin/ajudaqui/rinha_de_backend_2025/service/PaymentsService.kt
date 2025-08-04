package ajudaqui.rinha_de_backend_2025.service

import ajudaqui.rinha_de_backend_2025.dto.PaymentDto
import ajudaqui.rinha_de_backend_2025.dto.SummaryDto
import ajudaqui.rinha_de_backend_2025.entity.Payments
import ajudaqui.rinha_de_backend_2025.repository.PaymentsRepository
import java.time.Instant
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.stereotype.Service

@Service
class PaymentsService(
        val repository: PaymentsRepository,
        private val redisTemplate: ReactiveRedisTemplate<String, Payments>
) {

  suspend fun recivedTest(default: Boolean, paymentDto: PaymentDto): Payments =
          recivedInternal(default, paymentDto)

  suspend fun recived(paymentDto: PaymentDto): Payments {
    val default = true

    return recivedInternal(default, paymentDto)
  }
  private suspend fun recivedInternal(default: Boolean, paymentDto: PaymentDto): Payments =
          repository.save(
                  Payments(
                          correlationId = paymentDto.correlationId,
                          amount = paymentDto.amount,
                          default = default
                  )
          )

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

package ajudaqui.rinha_de_backend_2025.service

import ajudaqui.rinha_de_backend_2025.dto.PaymentDto
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

  suspend fun recived(paymentDto: PaymentDto): Payments =
          repository.save(
                  Payments(correlationId = paymentDto.correlationId, amount = paymentDto.amount)
          )

  suspend fun findById(paymentId: String): Payments? = repository.findById(paymentId)

  suspend fun findByPeriod(from: Instant, to: Instant): List<Payments> =
          repository.findByPeriod(from, to)
}

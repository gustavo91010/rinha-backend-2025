package ajudaqui.rinha_de_backend_2025.entity

import ajudaqui.rinha_de_backend_2025.dto.PaymentDto
import java.math.BigDecimal
import java.time.Instant
import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash("payments")
data class Payments(
        @Id val correlationId: String,
        val amount: BigDecimal,
        val default: Boolean,
        val requestedAt: Instant? = Instant.now()
) {
  companion object {
    fun from(dto: PaymentDto): Payments =
            Payments(correlationId = dto.correlationId, amount = dto.amount, default = true)
  }
}

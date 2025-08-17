package ajudaqui.rinha_de_backend_2025.entity

import ajudaqui.rinha_de_backend_2025.dto.PaymentDto
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal
import java.time.Instant
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding
import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash

@RegisterReflectionForBinding(Payments::class)
@RedisHash("payments")
data class Payments
@JsonCreator
constructor(
        @Id @JsonProperty("correlationId") val correlationId: String,
        @JsonProperty("amount") val amount: BigDecimal,
        @JsonProperty("default") val default: Boolean,
        @JsonProperty("requestedAt") val requestedAt: Instant?
) {
  companion object {
    fun from(dto: PaymentDto): Payments =
            Payments(
                    correlationId = dto.correlationId,
                    amount = dto.amount,
                    default = true,
                    requestedAt = Instant.now()
            )
  }
}

package ajudaqui.rinha_de_backend_2025.entity

import java.math.BigDecimal
import java.time.Instant
import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash("payments")
data class Payments(
        @Id val correlationId: String,
        val amount: BigDecimal,
        val default: Boolean,
        val createdAt: Instant? = Instant.now()
)

package ajudaqui.rinha_de_backend_2025.dto

import java.math.BigDecimal
import java.time.Instant

data class PaymentDto(
        val correlationId: String,
        val amount: BigDecimal,
        // val requestedAt: Instant? = Instant.now()
)

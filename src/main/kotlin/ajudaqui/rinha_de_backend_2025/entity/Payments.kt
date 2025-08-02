package ajudaqui.rinha_de_backend_2025.entity

import java.util.UUID
import java.math.BigDecimal

data class Payments(
        val correlationId: UUID,
        val amount: BigDecimal,
)

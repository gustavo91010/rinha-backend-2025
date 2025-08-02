package ajudaqui.rinha_de_backend_2025.dto

import java.util.UUID
import java.math.BigDecimal

class PaymentDto(val correlationId: UUID, val amount: BigDecimal)

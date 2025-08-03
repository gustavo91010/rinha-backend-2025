package ajudaqui.rinha_de_backend_2025.repository

import ajudaqui.rinha_de_backend_2025.entity.Payments
import java.time.Instant

interface PaymentsRepository {
  suspend fun save(payment: Payments): Payments
  suspend fun findById(id: String): Payments?
  suspend fun findByPeriod(from: Instant, to: Instant): List<Payments>
}

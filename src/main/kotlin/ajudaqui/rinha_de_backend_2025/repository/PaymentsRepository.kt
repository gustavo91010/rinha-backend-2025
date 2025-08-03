package ajudaqui.rinha_de_backend_2025.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ajudaqui.rinha_de_backend_2025.entity.Payments

@Repository
interface PaymentsRepository: CrudRepository<Payments, Long>

package ajudaqui.rinha_de_backend_2025.service

import ajudaqui.rinha_de_backend_2025.dto.PaymentDto
import ajudaqui.rinha_de_backend_2025.entity.Payments
import ajudaqui.rinha_de_backend_2025.repository.PaymentsRepository
import org.springframework.stereotype.Service

@Service
class PaymentsService(val repository: PaymentsRepository) {

  fun recived(paymentDto: PaymentDto): String {

    val ap = mapOf("correlationId" to paymentDto.correlationId, "amount" to paymentDto.amount)
    print(ap)
    val um =
            repository.save(
                    Payments(correlationId = paymentDto.correlationId, amount = paymentDto.amount)
            )
    println("hummm $um")
    return "ha!"
  }

  fun findById(paymentId:Long)=repository.findById(paymentId)
  // resposta
  //     Qualquer resposta na faixa 2XX (200, 201, 202, etc) é válida. O corpo da resposta não será
  // validado – pode ser qualquer coisa ou até vazio.
}

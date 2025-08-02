package ajudaqui.rinha_de_backend_2025.service

import ajudaqui.rinha_de_backend_2025.dto.PaymentDto
import org.springframework.stereotype.Service

@Service
class PaymentsService {

  fun recived(paymentDto: PaymentDto): String {

    val ap = mapOf("correlationId" to paymentDto.correlationId, "amount" to paymentDto.amount)
    print(ap)
    return "ha!"
  }

  // resposta
  //     Qualquer resposta na faixa 2XX (200, 201, 202, etc) é válida. O corpo da resposta não será
  // validado – pode ser qualquer coisa ou até vazio.
}

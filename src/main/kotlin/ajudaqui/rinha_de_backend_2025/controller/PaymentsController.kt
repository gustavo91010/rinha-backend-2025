package ajudaqui.rinha_de_backend_2025.controller

import ajudaqui.rinha_de_backend_2025.service.PaymentsService
import ajudaqui.rinha_de_backend_2025.dto.PaymentDto
import java.math.BigDecimal
import java.util.UUID
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestBody

@RestController
@RequestMapping("/payments")
class PaymentsController(val paymentsSerivce: PaymentsService) {

  @PostMapping("")
  fun recived(@RequestBody paymentDto:PaymentDto) =
         paymentsSerivce.recived(paymentDto)
}

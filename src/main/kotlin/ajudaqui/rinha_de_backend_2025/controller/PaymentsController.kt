package ajudaqui.rinha_de_backend_2025.controller

import ajudaqui.rinha_de_backend_2025.dto.PaymentDto
import ajudaqui.rinha_de_backend_2025.service.PaymentsService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/payments")
class PaymentsController(val paymentsSerivce: PaymentsService) {

  @PostMapping("")
  suspend fun recived(@RequestBody paymentDto: PaymentDto): ResponseEntity<Map<String, String>> =
          ResponseEntity.ok(paymentsSerivce.register(paymentDto))
}

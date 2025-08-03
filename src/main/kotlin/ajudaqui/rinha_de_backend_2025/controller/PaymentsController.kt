package ajudaqui.rinha_de_backend_2025.controller

import ajudaqui.rinha_de_backend_2025.dto.PaymentDto
import ajudaqui.rinha_de_backend_2025.service.PaymentsService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.PathVariable

@RestController
@RequestMapping("/payments")
class PaymentsController(val paymentsSerivce: PaymentsService) {

  @PostMapping("")
  fun recived(@RequestBody paymentDto: PaymentDto) = paymentsSerivce.recived(paymentDto)

  @GetMapping("/id/{paymentId}")
  fun findById(@PathVariable paymentId: Long) = paymentsSerivce.findById(paymentId)
}

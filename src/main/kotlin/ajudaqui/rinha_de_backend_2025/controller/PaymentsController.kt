package ajudaqui.rinha_de_backend_2025.controller

import ajudaqui.rinha_de_backend_2025.dto.PaymentDto
import ajudaqui.rinha_de_backend_2025.entity.Payments
import ajudaqui.rinha_de_backend_2025.service.PaymentsService
import java.time.Instant
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestParam

@RestController
@RequestMapping("/payments")
class PaymentsController(val paymentsSerivce: PaymentsService) {

  @PostMapping("")
  suspend  fun recived(@RequestBody paymentDto: PaymentDto) = paymentsSerivce.recived(paymentDto)

  @GetMapping("/id/{paymentId}")
  suspend  fun findById(@PathVariable paymentId: String) = paymentsSerivce.findById(paymentId)


  @GetMapping("/payments-summary")
  suspend fun findByPeriod(@RequestParam from: Instant,@RequestParam to: Instant): List<Payments> =
      paymentsSerivce.findByPeriod(from, to)
}

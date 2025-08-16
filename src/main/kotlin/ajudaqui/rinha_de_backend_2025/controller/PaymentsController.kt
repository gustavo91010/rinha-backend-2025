package ajudaqui.rinha_de_backend_2025.controller

import ajudaqui.rinha_de_backend_2025.dto.PaymentDto
import ajudaqui.rinha_de_backend_2025.entity.Payments
import ajudaqui.rinha_de_backend_2025.service.PaymentsService
import java.time.Instant
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/payments")
class PaymentsController(val paymentsSerivce: PaymentsService) {

  @PostMapping("")
  suspend fun recived(@RequestBody paymentDto: PaymentDto): ResponseEntity<Map<String, String>> =
          // ResponseEntity.ok(paymentsSerivce.recived(paymentDto))
          ResponseEntity.ok(paymentsSerivce.saveFirst(paymentDto))

  @PostMapping("/default/{default}")
  suspend fun recivedTest(
          @PathVariable default: Boolean,
          @RequestBody paymentDto: PaymentDto
  ): ResponseEntity<Payments> = ResponseEntity.ok(paymentsSerivce.recivedTest(default, paymentDto))

  @GetMapping("/id/{paymentId}")
  suspend fun findById(@PathVariable paymentId: String): ResponseEntity<Payments> =
          ResponseEntity.ok(paymentsSerivce.findById(paymentId))

  @GetMapping("/period")
  suspend fun findByPeriod(
          @RequestParam from: Instant,
          @RequestParam to: Instant
  ): ResponseEntity<List<Payments>> = ResponseEntity.ok(paymentsSerivce.findByPeriod(from, to))

  // @GetMapping("/payments-summary")
  // suspend fun summary(
  //         @RequestParam from: Instant,
  //         @RequestParam to: Instant
  // ): ResponseEntity<SummaryDto> = ResponseEntity.ok(paymentsSerivce.summary(from, to))
}

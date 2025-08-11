package ajudaqui.rinha_de_backend_2025.controller

import ajudaqui.rinha_de_backend_2025.dto.SummaryDto
import ajudaqui.rinha_de_backend_2025.service.PaymentsService
import java.time.Instant
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/payments-summary")
class SummaryController(val paymentsSerivce: PaymentsService) {

  @GetMapping("")
  suspend fun summary(
          @RequestParam from: Instant,
          @RequestParam to: Instant
  ): ResponseEntity<SummaryDto> = ResponseEntity.ok(paymentsSerivce.summary(from, to))
}

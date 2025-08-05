package ajudaqui.rinha_de_backend_2025.config

import org.springframework.stereotype.Component
import ajudaqui.rinha_de_backend_2025.service.PaymentsService
import jakarta.annotation.PostConstruct

@Component
class PaymentsStartupRunner(private val paymentService: PaymentsService) {

  @PostConstruct
  fun star(){
    paymentService.startProcessing()
  }
  
}

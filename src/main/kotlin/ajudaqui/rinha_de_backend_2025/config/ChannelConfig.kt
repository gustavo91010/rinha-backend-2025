package ajudaqui.rinha_de_backend_2025.config

import kotlinx.coroutines.channels.Channel
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Bean
import ajudaqui.rinha_de_backend_2025.dto.PaymentDto
import ajudaqui.rinha_de_backend_2025.dto.PaymentTask

@Configuration
open class ChannelConfig {

  @Bean
  open fun paymentChannel(): Channel<PaymentTask> = Channel(capacity = Channel.UNLIMITED)
  // open fun paymentChannel(): Channel<PaymentDto> = Channel(capacity = Channel.UNLIMITED)
}

package ajudaqui.rinha_de_backend_2025.config

import kotlinx.coroutines.channels.Channel
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Bean
import ajudaqui.rinha_de_backend_2025.dto.PaymentDto

@Configuration
open class ChannelConfig {

  @Bean
  open fun paymentChannel(): Channel<PaymentDto> = Channel(capacity = Channel.UNLIMITED)
}

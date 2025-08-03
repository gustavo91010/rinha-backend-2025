package ajudaqui.rinha_de_backend_2025.client

import ajudaqui.rinha_de_backend_2025.dto.PaymentDto
import ajudaqui.rinha_de_backend_2025.dto.PaymentProcessorResponse
import kotlin.jvm.javaClass
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Component
class PaymentPocessor(
        @Qualifier("defaultClient") val defaultClient: WebClient,
        @Qualifier("fallbackClient") val fallbackClient: WebClient
) {

  val logger = LoggerFactory.getLogger(javaClass)

  fun postPayment(payload: PaymentDto, default: Boolean): Mono<PaymentProcessorResponse> {
    val webClient = if (default) defaultClient else fallbackClient
    return webClient
            .post()
            .uri("/payments")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(payload)
            .exchangeToMono { response -> handleResponde(response) }
  }

  private fun handleResponde(response: ClientResponse): Mono<PaymentProcessorResponse> {
    val statusCode = response.statusCode()
    return if (statusCode.is2xxSuccessful) {
      response.bodyToMono(PaymentProcessorResponse::class.java)
    } else {
      Mono.error(RuntimeException("Error $statusCode"))
    }
  }
}

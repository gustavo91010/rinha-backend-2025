package ajudaqui.rinha_de_backend_2025.client

import ajudaqui.rinha_de_backend_2025.dto.PaymentDto
import kotlin.jvm.javaClass
import kotlinx.coroutines.reactor.awaitSingle
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Component
class PaymentPocessor(
        @Qualifier("defaultClient") val defaultClient: WebClient,
        @Qualifier("fallbackClient") val fallbackClient: WebClient
) {

  val logger = LoggerFactory.getLogger(javaClass)

  fun postPayment(payload: PaymentDto, default: Boolean): Mono<Boolean> {
    val webClient = if (default) defaultClient else fallbackClient
    return webClient
            .post()
            .uri("/payments")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(payload)
            .exchangeToMono { response -> Mono.just(response.statusCode().is2xxSuccessful) }
  }

  suspend fun health(default: Boolean): Map<String, String> {

    val webClient = if (default) defaultClient else fallbackClient
    return webClient
            .get()
            .uri("/payments/service-health")
            .retrieve()
            .bodyToMono(object : ParameterizedTypeReference<Map<String, String>>() {})
            .awaitSingle()
  }
}

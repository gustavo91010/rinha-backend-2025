package ajudaqui.rinha_de_backend_2025.config

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

@Component
class LoggingWebFilter : WebFilter {

  private val logger = LoggerFactory.getLogger(LoggingWebFilter::class.java)

  override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
    val request = exchange.request
    val method = request.method
    val uri = request.uri.path

    logger.info("[$method] | $uri")

    return chain.filter(exchange)
  }
}

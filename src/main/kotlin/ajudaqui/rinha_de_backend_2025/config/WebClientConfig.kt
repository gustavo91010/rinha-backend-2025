package ajudaqui.rinha_de_backend_2025.config

import java.time.Duration
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.data.redis.serializer.StringRedisSerializer
import reactor.netty.http.client.HttpClient
import reactor.netty.resources.ConnectionProvider
import ajudaqui.rinha_de_backend_2025.entity.Payments

@Configuration
open class WebClientConfig {

  @Value("\${rinha.default.url}") private lateinit var defaultUrl: String
  @Value("\${rinha.fallback.url}") private lateinit var fallbackUrl: String

  private fun connectionProvider() =
          ConnectionProvider.builder("custom")
                  .maxConnections(100)
                  .pendingAcquireMaxCount(10000)
                  .pendingAcquireTimeout(Duration.ofSeconds(30))
                  .build()

  private fun httpClient() = HttpClient.create(connectionProvider()).keepAlive(false).compress(true)

  @Bean("defaultClient")
  open fun defaultWebClient() =
          WebClient.builder()
                  .clientConnector(ReactorClientHttpConnector(httpClient()))
                  .baseUrl(defaultUrl)
                  .build()

  @Bean("fallbackClient")
  open fun fallbackWebClient() =
          WebClient.builder()
                  .clientConnector(ReactorClientHttpConnector(httpClient()))
                  .baseUrl(fallbackUrl)
                  .build()

}

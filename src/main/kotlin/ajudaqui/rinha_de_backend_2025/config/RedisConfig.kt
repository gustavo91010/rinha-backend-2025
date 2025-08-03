package ajudaqui.rinha_de_backend_2025.config

import ajudaqui.rinha_de_backend_2025.entity.Payments
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer

@Suppress("DEPRECATION")
@Configuration
open class RedisConfig {

  @Bean
  open fun jackson2JsonRedisSerializer(): Jackson2JsonRedisSerializer<Any> {
    val serializer = Jackson2JsonRedisSerializer(Any::class.java)
    val mapper =
            ObjectMapper()
                    .registerModule(JavaTimeModule())
                    .registerModule(KotlinModule.Builder().build())
                    .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
    serializer.setObjectMapper(mapper)
    return serializer
  }

  @Bean
  open fun reactiveRedisTemplate(
          factory: ReactiveRedisConnectionFactory,
          jackson2JsonRedisSerializer: Jackson2JsonRedisSerializer<Any>
  ): ReactiveRedisTemplate<String, Payments> {
    @Suppress("UNCHECKED_CAST")
    val serializer = jackson2JsonRedisSerializer as Jackson2JsonRedisSerializer<Payments>

    val context =
            RedisSerializationContext.newSerializationContext<String, Payments>(
                            StringRedisSerializer()
                    )
                    .value(serializer)
                    .build()

    return ReactiveRedisTemplate(factory, context)
  }
}

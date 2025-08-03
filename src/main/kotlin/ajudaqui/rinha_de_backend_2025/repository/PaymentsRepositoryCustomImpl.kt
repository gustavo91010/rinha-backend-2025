package ajudaqui.rinha_de_backend_2025.repository

import ajudaqui.rinha_de_backend_2025.entity.Payments
import com.fasterxml.jackson.databind.ObjectMapper
import java.time.Instant
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.data.domain.Range
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.stereotype.Repository

@Repository
open class PaymentsRepositoryCustomImpl(
    private val redisTemplate: ReactiveRedisTemplate<String, Payments>,
    private val objectMapper: ObjectMapper
) : PaymentsRepository {

    companion object {
        private const val KEY = "PAYMENTS_BY_DATE"
    }

    override suspend fun save(payment: Payments): Payments {
        redisTemplate.opsForValue().set(payment.correlationId, payment).awaitSingle()
        redisTemplate
            .opsForZSet()
            .add(KEY, payment, payment.createdAt?.toEpochMilli()?.toDouble() ?: 0.0)
            .awaitSingle()
        return payment
    }

    override suspend fun findById(id: String): Payments? {
        return redisTemplate.opsForValue().get(id).awaitSingleOrNull()
    }

    override suspend fun findByPeriod(from: Instant, to: Instant): List<Payments> {
        val range: Range<Double> =
            Range.closed(from.toEpochMilli().toDouble(), to.toEpochMilli().toDouble())

        val rawList = redisTemplate.opsForZSet().rangeByScore(KEY, range).collectList().awaitSingle()

        return rawList.map<Any, Payments> { item ->
            when (item) {
                is Payments -> item
                is Map<*, *> -> objectMapper.convertValue(item, Payments::class.java)
                else -> throw IllegalStateException("Tipo inesperado: ${item::class}")
            }
        }
    }
}

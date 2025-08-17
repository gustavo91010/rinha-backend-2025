package ajudaqui.rinha_de_backend_2025.config

import ajudaqui.rinha_de_backend_2025.dto.*
import ajudaqui.rinha_de_backend_2025.entity.*
import org.springframework.aot.hint.RuntimeHints
import org.springframework.aot.hint.RuntimeHintsRegistrar
import org.springframework.context.annotation.ImportRuntimeHints

@ImportRuntimeHints(AppRuntimeHints::class)
class RuntimeHintsConfig

class AppRuntimeHints : RuntimeHintsRegistrar {
    override fun registerHints(hints: RuntimeHints, classLoader: ClassLoader?) {
        hints.reflection().registerType(Payments::class.java) { it.withMembers() }
        hints.reflection().registerType(SummaryDto::class.java) { it.withMembers() }
        hints.reflection().registerType(PaymentDto::class.java) { it.withMembers() }
    }
}


package ajudaqui.rinha_de_backend_2025.dto

import org.springframework.aot.hint.annotation.RegisterReflectionForBinding

@RegisterReflectionForBinding(SummaryDto::class)
class SummaryDto(val default: Map<String, String>, val fallback: Map<String, String>)

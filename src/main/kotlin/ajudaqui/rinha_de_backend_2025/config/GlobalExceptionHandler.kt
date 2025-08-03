package ajudaqui.rinha_de_backend_2025.config

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    val logger = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(Exception::class)
    fun handleAllExceptions(ex: Exception): ResponseEntity<String> {
        logger.error("Erro interno: ${ex.message} code: ${HttpStatus.INTERNAL_SERVER_ERROR}")
        return ResponseEntity("Erro interno: ${ex.message}", HttpStatus.INTERNAL_SERVER_ERROR)
    }
}

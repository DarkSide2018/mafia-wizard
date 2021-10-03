package mafia.wizard.config

import mafia.wizard.openapi.models.RequestError
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    private val logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    @ExceptionHandler(NotFoundException::class)
    fun handleAllException(ex: Throwable): ResponseEntity<RequestError> {
        logger.error(ex.message, ex)
        return ResponseEntity(RequestError(ex.message), HttpStatus.NOT_FOUND)
    }
}

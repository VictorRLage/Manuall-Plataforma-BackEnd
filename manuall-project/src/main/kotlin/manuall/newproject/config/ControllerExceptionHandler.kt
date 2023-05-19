package manuall.newproject.config

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MissingRequestHeaderException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ControllerExceptionHandler {

    @ExceptionHandler(MissingRequestHeaderException::class)
    fun handleException(ex: MissingRequestHeaderException): ResponseEntity<String> {
        return ResponseEntity.status(401).build()
    }
}
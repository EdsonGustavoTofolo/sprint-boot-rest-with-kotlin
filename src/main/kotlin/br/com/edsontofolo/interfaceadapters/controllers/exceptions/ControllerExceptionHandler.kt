package br.com.edsontofolo.interfaceadapters.controllers.exceptions

import br.com.edsontofolo.usecases.exceptions.ResourceNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.util.*

@ControllerAdvice
class ControllerExceptionHandler: ResponseEntityExceptionHandler() {

    @ExceptionHandler(Exception::class)
    fun allExceptionsHandle(ex: Exception, request: WebRequest): ResponseEntity<ExceptionResponse> {
        val exceptionResponse = ExceptionResponse(
            500,
            Date(),
            ex.message,
            request.getDescription(false)
        )
        return ResponseEntity.internalServerError().body(exceptionResponse)
    }

    @ExceptionHandler(ResourceNotFoundException::class)
    fun resourceNotFoundExceptionHandle(ex: ResourceNotFoundException, request: WebRequest): ResponseEntity<ExceptionResponse> {
        val exceptionResponse = ExceptionResponse(
            404,
            Date(),
            ex.message,
            request.getDescription(false)
        )
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse)
    }

}
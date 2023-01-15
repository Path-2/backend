package ao.path2.app.handlers

import ao.path2.app.core.exceptions.ResourceExistsException
import ao.path2.app.core.exceptions.ResourceNotFoundException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler

import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.time.LocalDateTime

@RestControllerAdvice
class ExceptionHandler : ResponseEntityExceptionHandler() {

  @ExceptionHandler(ResourceNotFoundException::class)
  fun resourceNotFoundHandler(exception: java.lang.Exception, request: WebRequest): ResponseEntity<Any> {
    return handleExceptionInternal(exception, null, HttpHeaders(), HttpStatus.NOT_FOUND, request)
  }

  @ExceptionHandler(ResourceExistsException::class)
  fun resourceExistsHandler(exception: java.lang.Exception, request: WebRequest): ResponseEntity<Any> {
    return handleExceptionInternal(exception, null, HttpHeaders(), HttpStatus.BAD_REQUEST, request)
  }

  override fun handleExceptionInternal(
    ex: java.lang.Exception,
    body: Any?,
    headers: HttpHeaders,
    status: HttpStatus,
    request: WebRequest
  ): ResponseEntity<Any> {
    return ResponseEntity.status(status.value()).headers(headers)
      .body(ErrorDetails(status.value(), ex.message, LocalDateTime.now(), ex.cause?.message))
  }
}
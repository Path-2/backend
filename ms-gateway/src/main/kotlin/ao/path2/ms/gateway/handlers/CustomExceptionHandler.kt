package ao.path2.ms.gateway.handlers

import org.springframework.core.annotation.Order
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.time.LocalDateTime

@Order(Int.MIN_VALUE)
@RestControllerAdvice
class CustomExceptionHandler : ResponseEntityExceptionHandler() {

  @ExceptionHandler(Exception::class)
  fun globalHandler(exception: Exception, request: WebRequest) =
    handleExceptionInternal(exception, null, HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request)

  override fun handleExceptionInternal(
    ex: Exception,
    body: Any?,
    headers: HttpHeaders,
    status: HttpStatus,
    request: WebRequest
  ): ResponseEntity<Any> {
    return ResponseEntity.status(status.value()).headers(headers)
      .body(ErrorDetails(status.value(), ex.message, LocalDateTime.now(), ex.cause?.message))
  }
}
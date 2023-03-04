package ao.path2.ms.forgot.handlers


import ao.path2.ms.forgot.exceptions.ResourceExistsException
import ao.path2.ms.forgot.exceptions.ResourceNotFoundException
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.security.SignatureException
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
class ExceptionHandler : ResponseEntityExceptionHandler() {

  @ExceptionHandler(ResourceNotFoundException::class)
  fun resourceNotFoundHandler(exception: Exception, request: WebRequest): ResponseEntity<Any> {
    return handleExceptionInternal(exception, null, HttpHeaders(), HttpStatus.NOT_FOUND, request)
  }

  @ExceptionHandler(*[ResourceExistsException::class, ExpiredJwtException::class, MalformedJwtException::class, JwtException::class, SignatureException::class])
  fun resourceExistsHandler(exception: Exception, request: WebRequest): ResponseEntity<Any> {
    return handleExceptionInternal(exception, null, HttpHeaders(), HttpStatus.BAD_REQUEST, request)
  }

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
    return ResponseEntity
      .status(getStatus(ex.message ?: "", status))
      .headers(headers)
      .body(ErrorDetails(getStatus(ex.message ?: "unknown", status), ex.message ?: "unknown", LocalDateTime.now(), ex.cause?.message))
  }

  fun getStatus(message: String, status: HttpStatus) =
    if (message.startsWith("Access is d")) HttpStatus.FORBIDDEN.value() else status.value();

}
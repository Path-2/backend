package ao.path2.ms.forgot.handlers

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.springframework.http.HttpStatus
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import java.time.LocalDateTime
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class CustomAuthenticationFailureHandler : AuthenticationFailureHandler, AccessDeniedHandler {

  private val mapper = ObjectMapper()

  override fun onAuthenticationFailure(
    request: HttpServletRequest?,
    response: HttpServletResponse?,
    exception: AuthenticationException?
  ) {
    val httpStatus = HttpStatus.UNAUTHORIZED; // 401

    handler(exception, response, httpStatus)
  }

  override fun handle(
    request: HttpServletRequest?,
    response: HttpServletResponse?,
    exception: AccessDeniedException?
  ) {
    val httpStatus = HttpStatus.FORBIDDEN; // 401

    handler(exception, response, httpStatus)
  }

  private fun handler(ex: Exception?, response: HttpServletResponse?, status: HttpStatus) {

    mapper.registerModule(JavaTimeModule())
    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)

    val data = ErrorDetails(status.value(), ex?.message, LocalDateTime.now(), ex?.cause?.message)

    // setting the response HTTP status code
    response?.status = status.value();

    // serializing the response body in JSON
    response
      ?.outputStream
      ?.println(
        mapper.writeValueAsString(data)
      );
  }
}
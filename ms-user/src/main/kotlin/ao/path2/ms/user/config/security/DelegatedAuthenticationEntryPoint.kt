package ao.path2.ms.user.config.security

import ao.path2.ms.user.handlers.ErrorDetails
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import java.io.IOException
import java.time.LocalDateTime
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component("delegatedAuthenticationEntryPoint")
class DelegatedAuthenticationEntryPoint : AuthenticationEntryPoint {

  @Throws(IOException::class, ServletException::class)
  override fun commence(
    request: HttpServletRequest?,
    response: HttpServletResponse?,
    ex: AuthenticationException?
  ) {

    val status = HttpStatus.UNAUTHORIZED

    val mapper = ObjectMapper()
    mapper.registerModule(JavaTimeModule())
    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)

    val data = ErrorDetails(
      getStatus(ex?.message ?: "", status),
      ex?.message ?: "",
      LocalDateTime.now(),
      ex?.cause?.message
    )

    // setting the response HTTP status code
    response?.status = getStatus(ex?.message ?: "", status)

    // setting content type
    response?.setHeader("Content-Type", "application/json")

    // serializing the response body in JSON
    response
      ?.outputStream
      ?.println(
        mapper.writeValueAsString(data)
      );
  }

  fun getStatus(message: String, status: HttpStatus) =
    if (message.startsWith("Access is d")) HttpStatus.FORBIDDEN.value() else status.value();

}
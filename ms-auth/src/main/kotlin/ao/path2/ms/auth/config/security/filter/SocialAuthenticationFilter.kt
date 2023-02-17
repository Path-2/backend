package ao.path2.ms.auth.config.security.filter

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JSR310Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.springframework.http.HttpStatus
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

interface SocialAuthenticationFilter {
  fun extractTokenIfNotNull(request: HttpServletRequest): String?
  fun stringfy(obj: Any): String = ObjectMapper().registerModule(JavaTimeModule()).writeValueAsString(obj)
  fun populateResponse(response: HttpServletResponse, data: Any, httpStatus: HttpStatus) {
    response.status = httpStatus.value()
    response.writer.append(stringfy(data))
  }
}
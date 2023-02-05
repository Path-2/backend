package ao.path2.ms.auth.config.security.filter

import ao.path2.ms.auth.config.security.model.UserLoginDto
import ao.path2.ms.auth.config.security.model.UserSecurity
import ao.path2.ms.auth.core.JWSAuthToken
import ao.path2.token.JwtToken
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtAuthenticationFilter(
  private val jwtTokenUtil: JwtToken,
  private val authenticationManager: AuthenticationManager
) :
  UsernamePasswordAuthenticationFilter() {

  override fun attemptAuthentication(req: HttpServletRequest, response: HttpServletResponse): Authentication {
    val credentials = ObjectMapper().readValue(req.inputStream, UserLoginDto::class.java)
    println(credentials.username)

    if(credentials.username.isEmpty() || credentials.username.isBlank()) {

    }

    val auth = UsernamePasswordAuthenticationToken(
      credentials.username,
      credentials.password,
      listOf()
    )
    return authenticationManager.authenticate(auth)
  }

  override fun successfulAuthentication(
    req: HttpServletRequest?, res: HttpServletResponse, chain: FilterChain?,
    auth: Authentication
  ) {
    val username = (auth.principal as UserSecurity).username
    val token: String = jwtTokenUtil.generateToken(username)
    res.addHeader("Authorization", token)
    res.addHeader("AuthorizationType", "Bearer")
    res.addHeader("Access-Control-Expose-Headers", "Authorization")

    res.writer.write(stringfy(JWSAuthToken(token)))
  }

  override fun unsuccessfulAuthentication(
    request: HttpServletRequest,
    response: HttpServletResponse,
    failed: AuthenticationException
  ) {
    val error = BadCredentialsError()
    response.status = error.status
    response.contentType = "application/json"
    response.writer.append(error.toString())
  }

  private data class BadCredentialsError(
    val timestamp: Long = Date().time,
    val status: Int = 401,
    val message: String = "Email or password incorrect",
  ) {
    override fun toString(): String {
      return ObjectMapper().writeValueAsString(this)
    }
  }

  private fun stringfy(obj: Any): String = ObjectMapper().writeValueAsString(obj)

}
package ao.path2.ms.auth.config.security.filter

import ao.path2.ms.auth.config.security.model.SocialUserData
import ao.path2.ms.auth.core.JWSAuthToken
import ao.path2.ms.auth.handlers.ErrorDetails
import ao.path2.ms.auth.repository.UserRepository
import ao.path2.ms.auth.token.JwtToken
import ao.path2.ms.auth.utils.security.getFacebookAuthURL
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.HttpStatus
import org.springframework.web.client.RestTemplate
import org.springframework.web.filter.OncePerRequestFilter
import java.time.LocalDateTime
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class FacebookAuthenticationFilter(
  private val restTemplate: RestTemplate,
  private val jwtToken: JwtToken,
  private val userRepository: UserRepository
) : OncePerRequestFilter(), SocialAuthenticationFilter {

  override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {

    val fbToken = extractTokenIfNotNull(request)

    if (fbToken != null) {
      try {
        //get data from Facebook
        val res =
          restTemplate.getForEntity(getFacebookAuthURL(fbToken), SocialUserData::class.java)

        if (res.statusCode == HttpStatus.OK) {
          val body = res.body

          if (!userRepository.existsByFacebookId(body?.id!!)) {
            populateResponse(
              response, ErrorDetails(
                401,
                "Cannot authenticate user",
                LocalDateTime.now(),
                "Invalid token"
              ), HttpStatus.UNAUTHORIZED
            )
            return
          }

          val user = userRepository.findByFacebookId(body.id!!)

          val roles = listOf("ROLE_USER")

          val responseToken = jwtToken.generateToken(user.username, roles.toTypedArray())

          populateResponse(response, JWSAuthToken(responseToken), HttpStatus.OK)

          return

        }
      } catch (err: Exception) {
        populateResponse(
          response,
          ErrorDetails(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            err.message,
            LocalDateTime.now(),
            err.cause.toString()
          ),
          HttpStatus.INTERNAL_SERVER_ERROR
        )
      }

      filterChain.doFilter(request, response)

    }

  }

  override fun extractTokenIfNotNull(request: HttpServletRequest): String? = request.getHeader("facebook_token")
}
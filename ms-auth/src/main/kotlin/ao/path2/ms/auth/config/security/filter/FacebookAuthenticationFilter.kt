package ao.path2.ms.auth.config.security.filter

import ao.path2.ms.auth.config.security.model.SocialUserData
import ao.path2.ms.auth.core.JWSAuthToken
import ao.path2.ms.auth.handlers.ErrorDetails
import ao.path2.ms.auth.repository.UserRepository
import ao.path2.ms.auth.token.JwtToken
import ao.path2.ms.auth.utils.security.getFacebookAuthURL
import com.fasterxml.jackson.databind.ObjectMapper
import me.paulschwarz.springdotenv.DotenvApplicationInitializer
import me.paulschwarz.springdotenv.DotenvConfig
import org.springframework.core.env.ConfigurableEnvironment
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
) : OncePerRequestFilter() {

  override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {

    val fbToken = extractTokenIfNotNull(request)

    if (fbToken != null) {
      //getFacebookAuthURL(fbToken)
      try {
        //get data from Facebook
        val res =
          restTemplate.getForEntity("http://localhost:9000/auth?access_token=$fbToken", SocialUserData::class.java)

        if (res.statusCode == HttpStatus.OK) {
          val body = res.body

          if (!userRepository.existsByFacebookId(body?.id!!)) {
            response.status = 401
            response.writer.append(
              stringfy(
                ErrorDetails(
                  401,
                  "Cannot authenticate user",
                  LocalDateTime.now(),
                  "Invalid token"
                )
              )
            )
            return
          }

          val user = userRepository.findByFacebookId(body.id!!)

          val responseToken = jwtToken.generateToken(user.username)

          response.status = 200
          response.writer.append(stringfy(JWSAuthToken(responseToken)))

          return

        }
      } catch (err: Exception) {
        print(err.message)
      }

      filterChain.doFilter(request, response)

    }


  }

  private fun stringfy(obj: Any): String = ObjectMapper().writeValueAsString(obj)

  private fun extractTokenIfNotNull(request: HttpServletRequest): String? = request.getHeader("facebook_token")

}
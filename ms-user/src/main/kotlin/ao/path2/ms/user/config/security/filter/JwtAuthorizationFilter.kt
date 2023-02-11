package ao.path2.ms.user.config.security.filter

import ao.path2.ms.user.token.JwtToken
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtAuthorizationFilter(
  private val jwtTokenUtil: JwtToken,
  private val service: UserDetailsService,
  authManager: AuthenticationManager,

  ) : BasicAuthenticationFilter(authManager) {

  @Throws(IOException::class, ServletException::class)
  override fun doFilterInternal(
    req: HttpServletRequest,
    res: HttpServletResponse,
    chain: FilterChain
  ) {
    val header = req.getHeader(HttpHeaders.AUTHORIZATION)
    if (header == null || !header.startsWith("Bearer ")) {
      chain.doFilter(req, res)
      return
    }
    getAuthentication(header.substring(7))?.also {
      SecurityContextHolder.getContext().authentication = it
    }
    chain.doFilter(req, res)
  }

  private fun getAuthentication(token: String): UsernamePasswordAuthenticationToken? {
    println(token)
    if (!jwtTokenUtil.isTokenValid(token)) return null
    val username = jwtTokenUtil.getUsername(token)
    val user = service.loadUserByUsername(username)

    return UsernamePasswordAuthenticationToken(user, null, user.authorities)
  }
}
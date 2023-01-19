package ao.path2.ms.user.config.security

import ao.path2.ms.user.config.security.filter.JwtAuthenticationFilter
import ao.path2.ms.user.config.security.filter.JwtAuthorizationFilter
import ao.path2.ms.user.utils.jwt.JwtTokenUtil
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain


@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
class SecurityConfig(
  private val userDetailsService: UserDetailsService,
) {
  private val jwtToken = JwtTokenUtil()

  private fun authManager(http: HttpSecurity): AuthenticationManager {
    val authenticationManagerBuilder = http.getSharedObject(
      AuthenticationManagerBuilder::class.java
    )
    authenticationManagerBuilder
      .userDetailsService(userDetailsService)
      .passwordEncoder(passwordEncoder())

    return authenticationManagerBuilder.build()
  }

  @Bean
  fun filterChain(http: HttpSecurity): SecurityFilterChain {
    val authenticationManager = authManager(http)
    // Put your endpoint to create/sign, otherwise spring will secure it as
    // well you won't be able to do any request
    http.authorizeRequests()
      .antMatchers(HttpMethod.POST, "/api/v1/users").permitAll()
      .anyRequest().authenticated()
      .and()
      .csrf().disable()
      .authenticationManager(authenticationManager)
      .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      .and()
      .addFilter(JwtAuthenticationFilter(jwtToken, authenticationManager))
      .addFilter(JwtAuthorizationFilter(jwtToken, userDetailsService, authenticationManager))

    return http.build()
  }

  @Bean
  fun passwordEncoder(): BCryptPasswordEncoder {
    return BCryptPasswordEncoder(10)
  }
}
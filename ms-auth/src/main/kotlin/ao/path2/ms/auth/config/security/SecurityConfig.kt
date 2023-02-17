package ao.path2.ms.auth.config.security

import ao.path2.ms.auth.config.security.filter.FacebookAuthenticationFilter
import ao.path2.ms.auth.config.security.filter.GoogleAuthenticationFilter
import ao.path2.ms.auth.config.security.filter.JwtAuthenticationFilter
import ao.path2.ms.auth.handlers.CustomAuthenticationFailureHandler
import ao.path2.ms.auth.repository.UserRepository
import ao.path2.ms.auth.token.JwtToken
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.client.RestTemplate

@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
class SecurityConfig(
  private val userDetailsService: UserDetailsService,
  private val jwtToken: JwtToken,
  private val userRepository: UserRepository
) {
  @Autowired
  @Qualifier("delegatedAuthenticationEntryPoint")
  var authEntryPoint: AuthenticationEntryPoint? = null

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
    http.csrf().disable()
      .cors()
      .and()
      .authorizeRequests()
      .antMatchers("/login").permitAll()
      .anyRequest().authenticated()
      .and()
      .authenticationManager(authenticationManager)
      .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      .and()
      .exceptionHandling()
      .accessDeniedHandler(securityExceptionHandler())
      .authenticationEntryPoint(authEntryPoint)
      .and()
      .addFilter(JwtAuthenticationFilter(jwtToken, authenticationManager))
      .addFilterBefore(
        FacebookAuthenticationFilter(restTemplate(), jwtToken, userRepository),
        JwtAuthenticationFilter::class.java
      ).addFilterBefore(
        GoogleAuthenticationFilter(restTemplate(), jwtToken, userRepository),
        FacebookAuthenticationFilter::class.java
      )

    return http.build()
  }

  @Bean
  fun restTemplate() = RestTemplate()

  @Bean
  fun passwordEncoder(): BCryptPasswordEncoder {
    return BCryptPasswordEncoder(10)
  }

  @Bean
  fun securityExceptionHandler() = CustomAuthenticationFailureHandler()
}
package ao.path2.ms.gateway.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.web.cors.reactive.CorsUtils
import org.springframework.web.reactive.config.WebFluxConfigurer
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono


@Configuration
class CORSConfiguration : WebFluxConfigurer {
  private val ALLOWED_HEADERS =
    "x-requested-with, authorization, Content-Type, Content-Length, Authorization, credential, X-XSRF-TOKEN"
  private val ALLOWED_METHODS = "GET, PUT, POST, DELETE, OPTIONS, PATCH"
  private val ALLOWED_ORIGIN = "*"
  private val MAX_AGE = "7200" //2 hours (2 * 60 * 60)


  @Bean
  fun corsFilter(): WebFilter? {
    return WebFilter { ctx: ServerWebExchange, chain: WebFilterChain ->
      val request: ServerHttpRequest = ctx.request
      if (CorsUtils.isCorsRequest(request)) {
        val response: ServerHttpResponse = ctx.response
        val headers: HttpHeaders = response.headers
        //headers.add("Access-Control-Allow-Origin", ALLOWED_ORIGIN)
        headers.add("Access-Control-Allow-Methods", ALLOWED_METHODS)
        headers.add(
          "Access-Control-Max-Age",
          MAX_AGE
        ) //OPTION how long the results of a preflight request (that is the information contained in the Access-Control-Allow-Methods and Access-Control-Allow-Headers headers) can be cached.
        headers.add("Access-Control-Allow-Headers", ALLOWED_HEADERS)
        if (request.method == HttpMethod.OPTIONS) {
          response.statusCode = HttpStatus.OK
          return@WebFilter Mono.empty<Void>()
        }
      }
      chain.filter(ctx)
    }
  }
}
package ao.path2.ms.gateway.handlers

import org.springframework.boot.autoconfigure.web.WebProperties
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler
import org.springframework.boot.web.error.ErrorAttributeOptions
import org.springframework.boot.web.reactive.error.ErrorAttributes
import org.springframework.context.ApplicationContext
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.codec.ServerCodecConfigurer
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.*
import reactor.core.publisher.Mono


@Component
@Order(-2)
class GlobalErrorWebExceptionHandler(
  globalErrorAttributes: GlobalErrorAttributes?, applicationContext: ApplicationContext?,
  serverCodecConfigurer: ServerCodecConfigurer
) : AbstractErrorWebExceptionHandler(globalErrorAttributes, WebProperties.Resources(), applicationContext) {
  init {
    super.setMessageWriters(serverCodecConfigurer.writers)
    super.setMessageReaders(serverCodecConfigurer.readers)
  }

  override fun getRoutingFunction(errorAttributes: ErrorAttributes?): RouterFunction<ServerResponse> {
    return RouterFunctions.route(RequestPredicates.all()) { request: ServerRequest ->
      renderErrorResponse(
        request
      )
    }
  }

  private fun renderErrorResponse(request: ServerRequest): Mono<ServerResponse?> {
    val errorPropertiesMap = getErrorAttributes(request, ErrorAttributeOptions.defaults())
    val statusCode = errorPropertiesMap["code"] as Int
    return ServerResponse.status(HttpStatus.valueOf(statusCode))
      .contentType(MediaType.APPLICATION_JSON)
      .body(BodyInserters.fromValue(errorPropertiesMap))
  }
}
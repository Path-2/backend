package ao.path2.ms.gateway.handlers

import org.springframework.boot.web.error.ErrorAttributeOptions
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes
import org.springframework.core.annotation.MergedAnnotations
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDateTime
import java.util.function.Consumer

@Component
class GlobalErrorAttributes : DefaultErrorAttributes() {
  override fun getErrorAttributes(request: ServerRequest, options: ErrorAttributeOptions): MutableMap<String, Any> {
    val error = getError(request)

    request.attributes().entries.forEach { o -> println("${o.key} -> ${o.value}") }

    val response = HashMap<String, Any>()

    response["code"] = determineHttpStatus(error).value()
    response["message"] = getMessage(error.message, request.path())
    response["timestamp"] = LocalDateTime.now()

    return response

  }

  private fun determineHttpStatus(error: Throwable): HttpStatus {
    return if (error is ResponseStatusException) error.status
    else getStatus(error)
  }

  private fun getStatus(error: Throwable): HttpStatus {
    return MergedAnnotations
      .from(error.javaClass, MergedAnnotations.SearchStrategy.TYPE_HIERARCHY)
      .get(ResponseStatus::class.java)
      .getValue("code", HttpStatus::class.java)
      .orElse(HttpStatus.INTERNAL_SERVER_ERROR)
  }

  private fun getMessage(message: String?, path: String): String {
    println(message)
    return if (message?.contains("404") == true)
      "Path not found: $path"
    else if (message?.contains("503") == true)
      message.substring(25, message.length - 1)
    else
      "Server internal error"
  }
}
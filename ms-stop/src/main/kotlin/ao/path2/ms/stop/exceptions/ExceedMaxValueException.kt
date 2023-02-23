package ao.path2.ms.stop.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
class ExceedMaxValueException(message: String) : RuntimeException(message)
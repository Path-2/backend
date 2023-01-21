package ao.path2.ms.user.core.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
class ResourceExistsException(message: String) : RuntimeException(message)
package ao.path2.ms.stop.dto

import kotlin.properties.Delegates

class StatusDto {
  var status by Delegates.notNull<Boolean>()
}

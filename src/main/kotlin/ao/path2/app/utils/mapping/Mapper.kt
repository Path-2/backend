package ao.path2.app.utils.mapping

import ao.path2.app.adapters.outbound.repository.entity.UserEntity
import ao.path2.app.core.domain.User
import org.springframework.beans.BeanUtils
import org.springframework.stereotype.Component

@Component
class Mapper {
    fun map(source: Any, target: Any): Any {
        BeanUtils.copyProperties(source, target)
        return target
    }
}
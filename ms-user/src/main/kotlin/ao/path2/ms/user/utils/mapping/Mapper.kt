package ao.path2.ms.user.utils.mapping

import org.springframework.beans.BeanUtils
import org.springframework.stereotype.Component

@Component
class Mapper {
    fun map(source: Any, target: Any): Any {
        BeanUtils.copyProperties(source, target)
        return target
    }

    fun map(source: Any, target: Any, transform: (data: Any) -> Unit): Any {
        BeanUtils.copyProperties(source, target)

        transform(target)

        return target
    }
}
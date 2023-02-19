package ao.path2.ms.user

import ao.path2.ms.user.controller.UserController
import ao.path2.ms.user.models.SocialDto
import ao.path2.ms.user.models.UserSource
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import java.util.*

@SpringBootTest
class Path2ApplicationTests {

  @Autowired
  lateinit var controller: UserController

  @Mock
  lateinit var mock: Any

  @Test
  fun createUserWithGoogle() {
    val token = UUID.randomUUID().toString()

    val data = SocialDto(token, UserSource.GOOGLE)

    val code = controller.saveWithSocialLogin(data).statusCode

    Assertions.assertEquals(HttpStatus.CREATED, code)
  }
}

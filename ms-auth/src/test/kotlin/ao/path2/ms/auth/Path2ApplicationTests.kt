package ao.path2.ms.auth

import ao.path2.ms.auth.entity.User
import ao.path2.ms.auth.repository.UserRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.web.client.RestTemplate

@SpringBootTest
class Path2ApplicationTests {
	@Autowired
	private lateinit var repository: UserRepository

	@Test
	fun createUser_OK() {
		val user = User()

		user.name = "Dumildes Paulo"
		user.email = "dumi703@gmail.com"
		user.password = "1234567890"

		val rest = RestTemplate()

		val resp = rest.postForEntity("http://localhost:8080/api/v1/users", user, User::class.java)

		Assertions.assertEquals(resp.statusCode.value(), HttpStatus.CREATED.value())
	}
}

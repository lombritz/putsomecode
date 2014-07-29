import java.sql.{ Date => SqlDate }
import java.util.{NoSuchElementException, Calendar}
import models._
import org.scalatest.{Tag, FunSuite}
import org.scalatest.mock.MockitoSugar
import org.mockito.Mockito._

/**
 * Created by jaime on 7/22/14.
 */
class UserServiceSpec extends FunSuite with MockitoSugar {
  object UserServiceMock {
    val userService = mock[UserService]
    private val users = List[User](
        User(1L, "Jaime", "Rojas", "lombritz", "123456", new SqlDate(Calendar.getInstance.getTimeInMillis), None),
        User(2L, "Luis", "Cortines", "l.cortines", "654321", new SqlDate(Calendar.getInstance.getTimeInMillis), None)
      )
    when(userService.list()).thenReturn(Page(users, 0, 0L, 2L))
    when(userService.findByUsername("lombritz")).thenReturn(Some(users(0)))
    when(userService.findByUsername("rolander09")).thenReturn(None)
  }

  import UserServiceMock._

  test("I should be able to find all users") {
    val users = userService.list()
    assert(users.items.size === 2)
  }

  test("I should be able to find user by username") {
    val user = userService.findByUsername("lombritz").get
    assert(user.username == "lombritz")
  }

  test("I should get NoSuchElementException when finding a user that doesn't exist") {
    intercept[NoSuchElementException] {
      userService.findByUsername("rolander09").get
    }
  }
}

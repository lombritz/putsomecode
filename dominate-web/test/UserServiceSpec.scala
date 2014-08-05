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
        User(1L, "Jaime", "Rojas", "lombritz@gmail.com", "123456", new SqlDate(Calendar.getInstance.getTimeInMillis), None),
        User(2L, "Luis", "Cortines", "l.cortines@gmail.com", "654321", new SqlDate(Calendar.getInstance.getTimeInMillis), None)
      )
    when(userService.page()).thenReturn(Page(users, 0, 0L, 2L))
    when(userService.findByEmail("lombritz@gmail.com")).thenReturn(Some(users(0)))
    when(userService.findByEmail("rolander09@hotmail.com")).thenReturn(None)
  }

  import UserServiceMock._

  test("I should be able to find all users") {
    val users = userService.page()
    assert(users.items.size === 2)
  }

  test("I should be able to find user by username") {
    val user = userService.findByEmail("lombritz@gmail.com").get
    assert(user.email == "lombritz@gmail.com")
  }

  test("I should get NoSuchElementException when finding a user that doesn't exist") {
    intercept[NoSuchElementException] {
      userService.findByEmail("rolander09").get
    }
  }
}

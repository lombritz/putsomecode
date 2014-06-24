import org.scalatest._
import models.User
import scala.slick.lifted.TableQuery
import models.UsersTable
import java.sql.Date
import java.util.Calendar
import play.api.db.slick.DBAction

class DBSuite extends FunSuite {
  
  val users = TableQuery[UsersTable]// TODO: initialize DB for test.
  
  test("I should be able to create an user") {
    val user = User(0L, "John", "Smith", "user1", "123", new Date(Calendar.getInstance().getTimeInMillis()), None)
    // TODO: implement
  }
  
}
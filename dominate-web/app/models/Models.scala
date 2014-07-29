package models

import play.api.db.slick.Config.driver.simple._
import play.api.db.slick.DB
import java.sql.Date

case class Page[A](items: List[A], page: Int, offset: Long, total: Long) {
  lazy val prev = Option(page - 1).filter(_ >= 0)
  lazy val next = Option(page + 1).filter(_ => (offset + items.size) < total)
}

/** User type definitions.
  * - Model
  * - Table Mapping
  * - Service
  */
case class User(id: Long, firstname: String, lastname: String, username: String, password: String,
                dateCreated: Date, dateModified: Option[Date])

class UsersTable(tag: Tag) extends Table[User](tag, "USER") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def firstname = column[String]("firstname", O.NotNull)
  def lastname = column[String]("lastname", O.NotNull)
  def username = column[String]("username", O.NotNull)
  def password = column[String]("password", O.NotNull)
  def dateCreated = column[Date]("date_created", O.NotNull)
  def dateModified = column[Option[Date]]("date_modified", O.Nullable)

  def * = (id, firstname, lastname, username, password, dateCreated, dateModified) <> (User.tupled, User.unapply _)
}

trait UserRepository {
  def findById(id: Long): Option[User]
  def findByUsername(username: String): Option[User]
  def save(user: User)
  def update(user: User)
  def list(page: Int = 0, pageSize: Int = 10, orderBy: Int = 1, filter: String = "%"): Page[User]
}

class SlickUserRepository extends UserRepository {
  import play.api.Play.current

  val users = TableQuery[UsersTable]

  def findById(id: Long): Option[User] =
    DB.withSession {
      implicit session =>
        users.filter(_.id === id).firstOption
    }

  def findByUsername(username: String): Option[User] =
    DB.withSession {
      implicit session =>
        users.filter(_.username.toLowerCase like username.toLowerCase).firstOption
    }

  def count: Int =
    DB.withSession {
      implicit session =>
        Query(users.length).first
    }

  def count(filter: String): Int =
    DB.withSession {
      implicit session =>
        Query(users.filter(_.firstname.toLowerCase like filter.toLowerCase).length).first
    }

  def list(page: Int = 0, pageSize: Int = 10, orderBy: Int = 1, filter: String = "%"): Page[User] = {
    DB.withSession {
      implicit session =>
        val offset = pageSize * page
        val query =
          (for {
            user <- users
            if user.firstname.toLowerCase like filter.toLowerCase
          } yield user).drop(offset).take(pageSize)
        val totalRows = count(filter)
        val result = query.list
        Page(result, page, offset, totalRows)
    }
  }

  def save(user: User) =
    DB.withSession {
      implicit session =>
        users.insert(user)
    }

  def update(user: User) =
    DB.withSession {
      implicit session =>
        users.update(user)
    }

}

class UserService(userRepository: UserRepository) {
  def findByUsername(username: String): Option[User] =
    userRepository.findByUsername(username)

  def save(user: User) =
    userRepository.save(user)

  def update(user: User) =
    userRepository.update(user)

  def list(page: Int = 0, pageSize: Int = 10, orderBy: Int = 1, filter: String = "%"): Page[User] =
    userRepository.list(page, pageSize, orderBy, filter)
}

/** Player type definitions.
  * - Model
  * - Table Mapping
  * - Service
  */
case class Player(id: Long, nickname: String, wins: Int, draws: Int, losses: Int, userID: Long)

class PlayersTable(tag: Tag) extends Table[Player](tag, "PLAYERS") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def nickname = column[String]("nickname", O.NotNull)
  def wins = column[Int]("wins", O.NotNull)
  def draws = column[Int]("draws", O.NotNull)
  def losses = column[Int]("losses", O.NotNull)
  def userID = column[Long]("user_id")

  def * = (id, nickname, wins, draws, losses, userID) <> (Player.tupled, Player.unapply _)

  def user = foreignKey("user_fk", userID, TableQuery[UsersTable])(_.id)
}

object PlayerService {

}
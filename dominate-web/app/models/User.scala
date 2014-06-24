package models

import play.api.db.slick.Config.driver.simple._
import java.sql.Date

case class User(id: Long, firstname: String, lastname: String, username: String, password: String, dateCreated: Date, dateModified: Option[Date])

/* Table mapping
 */
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

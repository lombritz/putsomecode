package controllers

import models._
import play.api.db.slick._
import play.api.db.slick.Config.driver.simple._
import play.api.data._
import play.api.data.Forms._
import play.api.mvc._
import play.api.Play.current
import play.api.libs.json.Json
import play.api.libs.json.Json._
import java.util.Calendar
import java.sql.Date

object UserController extends Controller {

  //create an instance of the table
  val users = TableQuery[UsersTable] //see a way to architect your app in the computers-database-slick sample  

  //JSON read/write macro
  implicit val catFormat = Json.format[User]

  def index = DBAction { implicit req =>
    Ok(views.html.users(users.list))
  }

  val userForm = Form(
    mapping(  
      "id" -> default(longNumber, 0L),
      "firstname" -> text,
      "lastname" -> text,
      "username" -> text,
      "password" -> text,
      "dateCreated" -> default(sqlDate, new Date(Calendar.getInstance().getTimeInMillis())),
      "dateModified" -> default(optional(sqlDate), None)
    )(User.apply)(User.unapply)
  )

  def insert = DBAction { implicit req =>
    val user = userForm.bindFromRequest.get
    users.insert(user)
    
    Redirect(routes.UserController.index)
  }

  def jsonFindAll = DBAction { implicit req =>
    Ok(toJson(users.list))
  }

  def jsonInsert = DBAction(parse.json) { implicit req =>
    req.request.body.validate[User].map { cat =>
        users.insert(cat)
        Ok(toJson(cat))
    }.getOrElse(BadRequest("invalid json"))    
  }

}
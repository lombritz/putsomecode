package controllers

import models._
import play.api.db.slick._
import play.api.data._
import play.api.data.Forms._
import play.api.mvc._
import play.api.Play.current
import play.api.libs.json.Json
import play.api.libs.json.Json._
import java.util.Calendar
import java.sql.Date

object UserController extends Controller {

  val userService = new UserService(new SlickUserRepository)

  //JSON read/write macro
  implicit val userFormat = Json.format[User]

  def index = DBAction { implicit req =>
    Ok(views.html.users(userService.page().items,
      List(
        Link("Home","#","", true),
        Link("Start game","#","",false),
        Link("Register", "#","",false),
        Link("Sign in", "#","",false)
      )
    ))
  }

  val userForm = Form(
    mapping(  
      "id" -> default(longNumber, 0L),
      "firstname" -> text,
      "lastname" -> text,
      "email" -> text,
      "password" -> text,
      "dateCreated" -> default(sqlDate, new Date(Calendar.getInstance().getTimeInMillis())),
      "dateModified" -> default(optional(sqlDate), None)
    )(User.apply)(User.unapply)
  )

  def insert = DBAction { implicit req =>
    val user = userForm.bindFromRequest.get
    userService.save(user)
    
    Redirect(routes.Application.index)
  }

  def jsonFindAll = DBAction { implicit req =>
    Ok(toJson(userService.page().items))
  }

  def jsonInsert = DBAction(parse.json) { implicit req =>
    req.request.body.validate[User].map { user =>
        userService.save(user)
        Ok(toJson(user))
    }.getOrElse(BadRequest("invalid json"))    
  }

}
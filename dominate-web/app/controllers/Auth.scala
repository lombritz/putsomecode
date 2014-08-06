package controllers

import controllers.UserController._
import play.api.Logger
import play.api.db.slick.DBAction
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import models._
import java.sql.Date
import java.util.Calendar
import scala.util.{Success, Try}

object Auth extends Controller {

  val userService = new UserService(new SlickUserRepository)

  val signupForm = Form(
    mapping(
      "id" -> default(longNumber, 0L),
      "firstname" -> text,
      "lastname" -> text,
      "email" -> email,
      "password" -> text,
      "dateCreated" -> default(sqlDate, new Date(Calendar.getInstance.getTimeInMillis)),
      "dateModified" -> default(optional(sqlDate), None)
    )(models.User.apply)(models.User.unapply)
  )

  val loginForm = Form(
    tuple(
      "email" -> email,
      "password" -> text
    )
  )

  def check(username: String, password: String) = username == "admin" && password == "1234"

  def register = Action {
    Ok(views.html.signup(signupForm)).flashing("info" -> "Register your account here!")
  }

  def signUp = DBAction { implicit request =>
    Logger.info("Sign Up in progress...")
    val user = userForm.bindFromRequest.get
    val resp = Try(userService.save(user))
    resp match {
      case Success(v) => Redirect(routes.Application.index).flashing("success" -> "Account successfully registered!")
      case _ => Redirect(routes.Auth.register).flashing("error" -> "Failed registering account!")
    }
  }

  def login = Action { implicit request =>
    Ok(views.html.login(loginForm))
  }

  def authenticate = Action { implicit request =>
    Logger.info("Authentication in progress...")
    loginForm.bindFromRequest.fold(
      formWithErrors => {
        Logger.info("Form invalid!")
        BadRequest(views.html.login(formWithErrors)).flashing("error" -> "Invalid form!")
      },
      user => {
        userService.findByEmail(user._1) match {
          case Some(_user) if user._2 == _user.password =>
            Logger.info(s"Succeeded authenticating user ${user._1}!")
            Redirect(routes.Application.index).withSession(Security.username -> user._1).flashing("success" -> "Login successful!")
          case _ =>
            Logger.info(s"Failed authenticating user ${user._1}!")
            Redirect(routes.Auth.login).flashing("error" -> "Invalid credentials!")
        }
      }
    )
  }

  def logout = Action {
    Redirect(routes.Auth.login).withNewSession.flashing("success" -> "Logout successful!")
  }
}

trait Secured {

  val userService = new UserService(new SlickUserRepository)

  def username(request: RequestHeader) = request.session.get(Security.username)

  def onUnauthorized(request: RequestHeader) = {
    Results.Redirect(routes.Auth.login).flashing("error" -> "Access unauthorized!")
  }

  def withAuth(f: => String => Request[AnyContent] => Result) = {
    Security.Authenticated(username, onUnauthorized) { user =>
      Action(request => f(user)(request))
    }
  }

  def withUser(f: User => Request[AnyContent] => Result) = withAuth {
    email => implicit request => {
      userService.findByEmail(email) match {
        case None => onUnauthorized(request)
        case Some(user) => f(user)(request)
      }
    }
  }


}
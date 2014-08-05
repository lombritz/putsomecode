package controllers

import controllers.UserController._
import models._
import play.api.db.slick.DBAction
import play.api.mvc._

object Application extends Controller with Secured {

  def index = Action { implicit request =>
    Ok(views.html.index(userService.page().items,
      List(
        Link("Home","#","", true),
        Link("Start game","#","",false),
        Link("Register", "#","",false),
        Link("Sign in", "#","",false)
      )
    ))
  }

  /** Security API */
  def singUp = DBAction { implicit request =>
    val user = userForm.bindFromRequest.get
    userService.save(user)

    Redirect(routes.Application.index)
  }

  /** Game API */
  def startGame = withUser { email => implicit request =>
    Ok("Hello " + email)
  }
}
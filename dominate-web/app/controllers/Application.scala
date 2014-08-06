package controllers

import models._
import play.api.mvc._

object Application extends Controller with Secured {

  def index = withUser { user => implicit req =>
    Ok(views.html.index(userService.page().items,
      List(
        Link("Home","#","", true),
        Link("Start game","#","",false),
        Link("Register", "#","",false),
        Link("Sign in", "#","",false)
      )
    ))
  }

  def startGame = withUser { user => implicit req =>
    Ok("Hello " + user)
  }
}
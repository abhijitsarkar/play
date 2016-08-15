package controllers

import play.api.mvc.{Action, Controller}

class Tester extends Controller {
  def handleRaw = Action(parse.raw) { implicit request =>
    request.body.asBytes().map(b => Ok(s"Request body has ${b.size} bytes")).getOrElse(NoContent)
  }
}

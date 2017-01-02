package controllers

import javax.inject.Inject

import play.api.libs.json.Json
import play.api.mvc.{Action, Controller, Results}
import services.IgService

import scala.concurrent.ExecutionContext

class IgController @Inject()(igService: IgService)(implicit context: ExecutionContext) extends Controller {

  def redirect = Action { implicit request =>
    Results.TemporaryRedirect(igService.authorizationUrl)
  }

  def callback(code: String) = Action.async { implicit request =>
    igService.callback(code)
      .map(m => Results.Ok(Json.toJson(m)))
  }
}

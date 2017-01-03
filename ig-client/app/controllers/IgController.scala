package controllers

import java.net.InetAddress
import javax.inject.Inject

import play.api.libs.json.Json
import play.api.mvc.{Action, Controller, RequestHeader, Results}
import services.IgService

import scala.concurrent.ExecutionContext

class IgController @Inject()(igService: IgService)(implicit context: ExecutionContext) extends Controller {
  def redirect = Action { implicit request =>
    Results.TemporaryRedirect(igService.authorizationUrl(callbackUrl(request)))
  }

  def callback(code: String) = Action.async { implicit request =>
    igService.callback(code, callbackUrl(request))
      .map(m => Results.Ok(Json.toJson(m)))
  }

  private def callbackUrl(implicit request: RequestHeader) = {
    // Compile first. Then locate the target/scala-2.xx/routes/main,
    // right click and Mark Directory as Generated Sources Root.
    val callbackUrl = controllers.routes.IgController.callback("whatever")
      .absoluteURL
      .takeWhile(_ != '?')

    callbackUrl.replaceFirst("localhost", InetAddress.getLocalHost.getHostAddress)
  }
}

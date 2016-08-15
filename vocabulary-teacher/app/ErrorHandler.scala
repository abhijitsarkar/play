import javax.inject.{Inject, Provider}

import play.api.http.DefaultHttpErrorHandler
import play.api.mvc.RequestHeader
import play.api.mvc.Results.NotFound
import play.api.routing.Router
import play.api.{Configuration, Environment, OptionalSourceMapper}

import scala.concurrent.Future

class ErrorHandler @Inject()(
                              env: Environment, config: Configuration,
                              sourceMapper: OptionalSourceMapper, router: Provider[Router]
                            ) extends DefaultHttpErrorHandler(env, config, sourceMapper, router) {
  override protected def onNotFound(request: RequestHeader, message: String) =
    Future.successful {
      NotFound(s"Could not find $request")
    }
}

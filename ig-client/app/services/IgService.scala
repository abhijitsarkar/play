package services

import com.google.inject.ImplementedBy
import models.{AccessToken, Media}
import play.api.libs.concurrent.Execution.Implicits.defaultContext

import scala.concurrent.Future

/**
  * @author Abhijit Sarkar
  */
@ImplementedBy(classOf[IgServiceInterpreter])
trait IgService {
  def authorizationUrl(callbackUrl: String): String

  protected def accessTokenParams(code: String, callbackUrl: String): Future[Map[String, Seq[String]]]

  protected def accessToken(params: Map[String, Seq[String]]): Future[AccessToken]

  protected def top(token: AccessToken): Future[Media]

  def callback(code: String, callbackUrl: String) = for {
    params <- accessTokenParams(code, callbackUrl)
    token <- accessToken(params)
    media <- top(token)
  } yield media
}

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
  def authorizationUrl: String

  def accessTokenParams(code: String): Future[Map[String, Seq[String]]]

  def accessToken(params: Map[String, Seq[String]]): Future[AccessToken]

  def top(token: AccessToken): Future[Media]

  def callback(code: String) = for {
    params <- accessTokenParams(code)
    token <- accessToken(params)
    media <- top(token)
  } yield media
}

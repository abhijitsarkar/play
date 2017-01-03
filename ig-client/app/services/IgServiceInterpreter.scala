package services

import java.net.{URLEncoder}
import java.nio.charset.StandardCharsets.UTF_8
import javax.inject.Inject

import models.{AccessToken, Media}
import org.slf4j.LoggerFactory
import play.api.Configuration
import play.api.libs.ws.WSClient

import scala.concurrent.{ExecutionContext, Future}

/**
  * @author Abhijit Sarkar
  */
class IgServiceInterpreter @Inject()(configuration: Configuration, ws: WSClient)(implicit context: ExecutionContext)
  extends IgService {
  private val logger = LoggerFactory.getLogger(classOf[IgServiceInterpreter])

  val clientId = configuration.getString("clientId").get
  val clientSecret = configuration.getString("clientSecret").get

  val ig = configuration.getConfig("instagram").get
  val authorizeUrl = ig.getString("authorizeUrl").get
  val recentPostsUrl = ig.getString("recentPostsUrl").get

  override def authorizationUrl(callbackUrl: String) = {
    val utf8Encode = (s: String) => URLEncoder.encode(s, UTF_8.name)
    val qs = (for {
      (k, v) <- Map(
        "client_id" -> clientId,
        "redirect_uri" -> callbackUrl,
        "response_type" -> "code"
      )
    } yield s"${utf8Encode(k)}=${utf8Encode(v)}").mkString("&")
    s"$authorizeUrl?$qs"
  }

  override def accessTokenParams(code: String, callbackUrl: String) =
    Future {
      Map(
        "client_id" -> Seq(clientId),
        "client_secret" -> Seq(clientSecret),
        "grant_type" -> Seq("authorization_code"),
        "redirect_uri" -> Seq(callbackUrl),
        "code" -> Seq(code)
      )
    }

  import AccessToken._
  import Media._
  import play.api.libs.json._

  override def accessToken(params: Map[String, Seq[String]]) = {
    val accessTokenUrl = ig.getString("accessTokenUrl").get
    ws.url(accessTokenUrl)
      .withHeaders(
        "Content-Type" -> "application/x-www-form-urlencoded",
        "Accept" -> "application/json"
      )
      .post(params)
      .transform(
        response => {
          val token = response.body
          logger.debug(s"Access token: ${
            token
          }")
          Json.parse(token).as[AccessToken]
        },
        t => {
          logger.error("Failed to retrieve access token.", t)
          t
        }
      )
  }

  override def top(token: AccessToken) = {
    ws.url(s"${
      recentPostsUrl
    }?access_token=${
      token.value
    }")
      .withHeaders(
        "Accept" -> "application/json"
      )
      .get()
      .transform(
        response => {
          val recent = response.body
          logger.debug(s"Recent posts: ${
            recent
          }")
          Json.parse(recent).as[Media]
        },
        t => {
          logger.error("Failed to retrieve recent posts.", t)
          t
        }
      )
  }
}

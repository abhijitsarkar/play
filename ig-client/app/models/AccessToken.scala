package models
// JSON library
import play.api.libs.json.Reads._
// Custom validation helpers
// Combinator syntax

import play.api.libs.json.{Reads, _}

/**
  * @author Abhijit Sarkar
  */
case class AccessToken(value: String)

object AccessToken {
  // Json combinators doesn't work for single field case class
  // http://stackoverflow.com/questions/14754092/how-to-turn-json-to-case-class-when-case-class-has-only-one-field
  implicit val accessTokenReads: Reads[AccessToken] = (__ \ "access_token").read[String].map(AccessToken(_))
}
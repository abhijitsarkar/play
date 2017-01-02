package models

import play.api.libs.functional.syntax._
import play.api.libs.json.{JsPath, Reads, Writes}

/**
  * @author Abhijit Sarkar
  */
case class Medium(link: String, likes: Long)

object Medium {
  implicit val mediumReads: Reads[Medium] =
    (
      (JsPath \ "link").read[String] and
        (JsPath \ "likes" \ "count").read[Long]
      ) (Medium.apply _)
  implicit val mediumWrites: Writes[Medium] =
    (
      (JsPath \ "link").write[String] and
        (JsPath \ "likes").write[Long]
      ) (unlift(Medium.unapply))
}

package models

import play.api.libs.functional.syntax._
import play.api.libs.json.{Reads, Writes, __}

/**
  * @author Abhijit Sarkar
  */
case class Media(media: Seq[Medium])

import models.Medium._

object Media {
  implicit val mediaReads: Reads[Media] = (__ \ "data").read[Seq[Medium]].map(Media(_))
  implicit val mediaWrites: Writes[Media] = (__ \ "data").write[Seq[Medium]].contramap { (m: Media) => m.media }
}
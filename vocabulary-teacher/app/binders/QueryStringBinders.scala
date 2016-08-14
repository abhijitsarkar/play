package binders

import play.api.i18n.Lang
import play.api.mvc.QueryStringBindable

/**
  * @author Abhijit Sarkar
  */
object QueryStringBinders {

  implicit object LangQueryStringBindable extends QueryStringBindable[Lang] {
    override def bind(key: String, params: Map[String, Seq[String]]) =
      Some(params(key).headOption.flatMap(Lang.get(_)).toRight(s"Language value is not recognized"))

    override def unbind(key: String, value: Lang) = value.code
  }

}

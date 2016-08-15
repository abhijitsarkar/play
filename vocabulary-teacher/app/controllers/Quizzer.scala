package controllers

import javax.inject.Inject

import play.api.i18n.Lang
import play.api.mvc.{Action, Controller}
import services.VocabularyService

class Quizzer @Inject()(service: VocabularyService) extends Controller {
  def quiz(sourceLanguage: Lang, targetLanguage: Lang) = Action { implicit request =>
    service.findRandomVocabulary(sourceLanguage, targetLanguage).map(
      v => Ok(s"Found word ${v.word} and translation ${v.translation}"))
      .getOrElse(NotFound)
  }

  def check(sourceLanguage: Lang, word: String, targetLanguage: Lang, translation: String) =
    Action { implicit request =>
      if (service.verify(sourceLanguage, word, targetLanguage, translation)) Ok
      else NotAcceptable
    }

  def quizh(sourceLanguage: Lang) = Action { implicit request =>
    val targetLanguage = Lang.get(request.headers("X-Target-Language")).getOrElse(Lang.defaultLang)
    service.findRandomVocabulary(sourceLanguage, targetLanguage).map(
      v => Ok(s"Found word ${v.word} and translation ${v.translation}"))
      .getOrElse(NotFound)
  }
}

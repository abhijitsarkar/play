package controllers

import javax.inject.Inject

import play.api.i18n.Lang
import play.api.mvc.{Action, Controller}
import services.VocabularyService

class Quizzer @Inject()(service: VocabularyService) extends Controller {
  def quiz(sourceLanguage: Lang, targetLanguage: Lang) = Action { implicit request =>
    service.findRandomVocabulary(sourceLanguage, targetLanguage).map(_ => Ok).getOrElse(NotFound)
  }

  def check(sourceLanguage: Lang, word: String, targetLanguage: Lang, translation: String) =
    Action { implicit request =>
      if (service.verify(sourceLanguage, word, targetLanguage, translation)) Ok
      else NotAcceptable
    }
}

package controllers

import javax.inject.Inject

import models.Vocabulary
import play.api.i18n.Lang
import play.api.mvc._
import services.VocabularyService

class Importer @Inject()(service: VocabularyService) extends Controller {
  def importWord(sourceLanguage: Lang, word: String, targetLanguage: Lang, translation: String) =
    Action { implicit request =>
      service.addVocabulary(
        Vocabulary(sourceLanguage, targetLanguage, word, translation)
      ) match {
        case true => Ok
        case _ => Conflict
      }
    }
}
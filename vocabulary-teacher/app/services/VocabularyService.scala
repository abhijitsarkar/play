package services

import javax.inject.Singleton

import models.Vocabulary
import play.api.i18n.Lang

import scala.util.Random

@Singleton
class VocabularyService {
  private val vocabularies = collection.mutable.Set(
    Vocabulary(Lang("en"), Lang("fr"), "hello", "bonjour"),
    Vocabulary(Lang("en"), Lang("fr"), "play", "jouer")
  )

  def addVocabulary(v: Vocabulary) = vocabularies.add(v)

  def findRandomVocabulary(sourceLanguage: Lang, targetLanguage: Lang) = {
    Random.shuffle(vocabularies.filter { v =>
      v.sourceLanguage == sourceLanguage && v.targetLanguage == targetLanguage
    }).headOption
  }

  def verify(sourceLanguage: Lang, word: String, targetLanguage: Lang, translation: String) =
    vocabularies.contains(Vocabulary(sourceLanguage, targetLanguage, word, translation))
}
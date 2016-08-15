package filters

import javax.inject.Inject

import akka.stream.Materializer
import akka.util.ByteString
import play.api.http.HttpEntity.Strict
import play.api.http.Status.{NOT_ACCEPTABLE, OK}
import play.api.mvc.{RequestHeader, _}

import scala.concurrent.{ExecutionContext, Future}

// Implementation 1

//class ScoreFilter @Inject()(implicit val mat: Materializer, ec: ExecutionContext) extends Filter {
//  override def apply(nextFilter: (RequestHeader) => Future[Result])(rh: RequestHeader) =
//    nextFilter(rh).flatMap { result =>
//      if (result.header.status == OK || result.header.status == NOT_ACCEPTABLE) {
//        val correct = result.session(rh).get("correct").getOrElse(0)
//        val wrong = result.session(rh).get("wrong").getOrElse(0)
//
//        val score = s"\nYour current score is: $correct correct " +
//          s"answers and $wrong wrong answers"
//
//        val contentType = result.body.contentType
//        val scoreByteString = ByteString(score)
//
//        result.body.consumeData.map(_.concat(scoreByteString)).map { newBody =>
//          result.copy(body = Strict(newBody, contentType))
//        }
//      } else {
//        Future.successful(result)
//      }
//    }
//}

// Implementation 2

class ScoreFilter @Inject()(implicit val mat: Materializer, ec: ExecutionContext) extends EssentialFilter {
  override def apply(nextFilter: EssentialAction) = new EssentialAction {
    def apply(requestHeader: RequestHeader) = {
      val accumulator = nextFilter(requestHeader)

      accumulator.mapFuture { result =>
        if (result.header.status == OK || result.header.status == NOT_ACCEPTABLE) {
          val correct = result.session(requestHeader).get("correct").getOrElse(0)
          val wrong = result.session(requestHeader).get("wrong").getOrElse(0)

          val score = s"\nYour current score is: $correct correct " +
            s"answers and $wrong wrong answers"

          val contentType = result.body.contentType
          val scoreByteString = ByteString(score)

          result.body.consumeData.map(_.concat(scoreByteString)).map { newBody =>
            result.copy(body = Strict(newBody, contentType))
          }
        } else {
          Future.successful(result)
        }
      }
    }
  }
}


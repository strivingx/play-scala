package controllers

import javax.inject._

import models.formats._
import models.forms._
import play.api.libs.json.Json
import play.api.mvc._
import services.BookService
import utils.Util

import scala.collection.JavaConversions._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class BookController @Inject()(bookService: BookService) extends Controller {
  val action = Action

  def before(): Unit = {
  }

  def getBookById(bookId: String) = action.async {
    bookService.getBookById(bookId).map { book =>
      Ok(Json.obj("book" -> book))
    }
  }

  def saveBook(bookId: String) = action.async { implicit req =>
    bookSaveForm.bindFromRequest().fold(
      error => {
        Future.successful(BadRequest(Json.obj("result" -> "failed")))
      },
      form => {
        bookService.saveBook(form.toBook("")).map { r =>
          Ok(Json.obj("result" -> "add book success"))
        }
      }
    )
  }

  def deleteBook(bookId: String) = action.async {
    bookService.deleteBook(bookId).map { r =>
      Ok(Json.obj("result" -> "delete book success"))
    }
  }

  val IS_REQUEST_DETAIL = "IS_REQUEST_DETAIL"


  def getBooksByRequest(pageNum: Option[Int], pageSize: Option[Int], sortBy: Option[String], order: Option[String]) = action.async {
    val request = Map[String, Object]()
    bookService.getBookCountByRequest(request).flatMap { count =>
      if (count > 0) {
        (request.getOrElse(IS_REQUEST_DETAIL, false).asInstanceOf[Boolean] match {
          case true => bookService.getBooksByRequest(request).map(_.map(Json.toJson(_)))
          case false => bookService.getBookListInfosByRequest(request).map(_.map(Json.toJson(_)))
        }).map { bookJson =>
          Ok(Json.obj("result" -> Util.toPageJsValue(Json.obj("books" -> bookJson), count, pageNum, pageSize)))
        }
      } else {
        Future.successful(Ok(""))
      }
    }
  }

}

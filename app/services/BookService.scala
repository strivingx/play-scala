package services

import models.cases.{Book, BookListInfo, BookRequest}

import scala.concurrent.Future

trait BookService {

  def saveBook(book: Book): Future[Int]

  def deleteBook(bookId: String): Future[Int]

  def getBookById(bookId: String): Future[Book]

  def getBooksByRequest(request: Map[String, Object]): Future[java.util.List[Book]]

  def getBookListInfosByRequest(request: Map[String, Object]): Future[java.util.List[BookListInfo]]

  def getBookCountByRequest(request: Map[String, Object]): Future[Int]

}
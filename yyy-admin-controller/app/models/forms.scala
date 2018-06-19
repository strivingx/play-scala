package models
import models.cases.Book
import play.api.data.Form
import play.api.data.Forms._

package object forms {
  val registerUserForm = Form(mapping(
    "username" -> nonEmptyText,
    "password" -> nonEmptyText
  )(RegisterUserForm.apply)(RegisterUserForm.unapply)
  )


  val bookSaveForm = Form(
    mapping(
      "name" -> nonEmptyText
    )(BookSaveForm.apply)(BookSaveForm.unapply)
  )

  case class BookSaveForm(name: String) {
    def toBook(bookId: String): Book = {
      new Book(bookId, name)
    }
  }
  case class RegisterUserForm(username: String, password: String)


}

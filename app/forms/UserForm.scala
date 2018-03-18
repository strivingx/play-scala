package forms
import models.User
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.Json

object UserForm {

  val registerUserForm = Form(mapping(
    "username" -> nonEmptyText,
    "password" -> nonEmptyText
  )(RegisterUserForm.apply)(RegisterUserForm.unapply)
  )

  case class RegisterUserForm(username: String, password: String)

}

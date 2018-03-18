package forms

import play.api.data.Form
import play.api.data.Forms._

object UserForm {

  val registerUserForm = Form(mapping(
    "username" -> nonEmptyText,
    "password" -> nonEmptyText
  )(RegisterUserForm.apply)(RegisterUserForm.unapply)
  )

  case class RegisterUserForm(username: String, password: String)

}

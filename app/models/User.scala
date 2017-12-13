package models

import play.api.libs.json.Json

case class User(var username: String, var password: String)   {
}

object User{

    implicit val userFormat = Json.format[User]

}
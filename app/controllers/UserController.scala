package controllers

import javax.inject._

import models.User
import models.User._
import play.api.libs.json.Json
import play.api.mvc._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
@Singleton
class UserController @Inject() extends Controller {

    def before(): Unit ={
    }
    def getUser = Action {
        val user = new User("yyy", "yyypwd")
        Ok(Json.obj("user" -> Json.toJson(user)))
    }

    def getUsers = Action {
        val user = new User("yyy", "yyypwd")
        val users = List(user,user,user)
        Ok(Json.obj("user" -> Json.toJson(users)))
    }

    def addUser = Action {
        val user = new User("yyy", "yyypwd")
        Ok(Json.obj("result" -> "add user success"))
    }

    def updateUser = Action {
        val user = new User("yyy", "yyypwd")
        Ok(Json.obj("result" -> "update user success"))
    }

    def deleteUser = Action {
        val user = new User("yyy", "yyypwd")
        Ok(Json.obj("result" -> "delete user success"))
    }

}

package controllers

import javax.inject._

import models.User
import models.User._
import play.api.libs.json.Json
import play.api.mvc._
import scala.concurrent.ExecutionContext.Implicits.global
@Singleton
class IndexController @Inject() extends Controller {

    def redirect = Action {
        Ok("111")
    }

    def forward = Action {
        val user = new User("yyy", "yyypwd")
        val users = List(user,user,user)
        Ok(Json.obj("user" -> Json.toJson(users)))
    }

}

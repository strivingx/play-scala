package controllers

import javax.inject._

import models.cases.{User, UserSimpleInfo}
import models.formats._
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.Json
import play.api.mvc._
import services.UserService
import utils.Util

import scala.collection.JavaConversions._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class UserController @Inject()(userService: UserService) extends Controller {
  implicit val userFormat = Json.format[User]
  implicit val userSimpleInfoFormat = Json.format[UserSimpleInfo]

  def getUserByUsername(username: String) = Action.async {
    userService.getUserByUsername(username).map { user =>
      Ok(Json.obj("user" -> user))
    }
  }

  case class JsonRequest(username: String)

  implicit val JsonRequestFormats = Json.format[JsonRequest]

  def getUsersByJson = Action(parse.json) { req =>
    val request = req.body.as[JsonRequest]
    Ok(Json.obj())
  }

  val registerUserForm = Form(
    mapping(
      "username" -> nonEmptyText,
      "password" -> nonEmptyText
    )(RegisterUserDTO.apply)(RegisterUserDTO.unapply)
  )

  case class RegisterUserDTO(username: String, password: String) {
    def toUser(): User = {
      new User(username = username, password = password)
    }
  }

  def registerUser = Action.async { implicit req =>
    registerUserForm.bindFromRequest().fold(
      error => {
        Future.successful(Ok(Json.obj("result" -> "failed")))
      },
      form => {
        userService.createUser(form.toUser()).map { r =>
          Ok(Json.obj("result" -> "add user success"))
        }
      }
    )
  }

  def updateUser(userId: String) = Action {
    Ok(Json.obj("result" -> "update user success"))
  }

  def deleteUser(userId: String) = Action.async {
    userService.deleteUser(userId).map { r =>
      Ok(Json.obj("result" -> "delete user success"))
    }
  }

  def getUsersByRequest(pageNum: Option[Int], pageSize: Option[Int], sortBy: Option[String], order: Option[String]) = Action.async {
    val request = Util.toUserRequest(pageNum, pageSize)
    userService.getUserCountByRequest(request).flatMap { count =>
      if (count > 0) {
        (request.is_list match {
          case true => userService.getUsersByRequest(request).map(_.map(Json.toJson(_)))
          case false => userService.getUserSimpleInfosByRequest(request).map(_.map(Json.toJson(_)))
        }).map { userJson =>
          Ok(Json.obj("result" -> Util.toPageJsValue(Json.obj("users" -> userJson), count, request.pageNum, request.pageSize)))
        }
      } else {
        Future.successful(Ok(""))
      }
    }
  }

  def index = Action {
    Redirect("/user/home")
  }

  def todo = TODO
}

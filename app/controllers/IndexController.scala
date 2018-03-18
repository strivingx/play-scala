package controllers

import javax.inject._

import play.api.libs.json.Json
import play.api.mvc._

@Singleton
class IndexController @Inject() extends Controller {

  def redirect = Action {
    Ok("111")
  }

  def forward = Action {
    Ok(Json.obj())
  }

}

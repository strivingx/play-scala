package utils

import models.cases.UserRequest
import play.api.libs.json.{JsObject, JsValue, Json}
object Util {

  def toUserRequest(pageNum: Option[Int], pageSize: Option[Int]): UserRequest = {
    val (num, size) = (pageNum.getOrElse(ConfigUtil.getDefaultPageNum), pageSize.getOrElse(ConfigUtil.getDefaultPageSize))
    new UserRequest(num, size)
  }
  def toPageJsValue(data: JsObject, totalCount: Int, pageNum: Int, pageSize: Int): JsValue = {
    data ++ Json.obj("total_count" -> totalCount, "page_num" -> pageNum, "page_size" -> pageSize)
  }
}

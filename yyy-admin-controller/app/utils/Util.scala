package utils

import java.text.SimpleDateFormat

import models.cases.RpcRequest
import play.api.libs.json._
object Util {

  def toUserRequest(pageNum: Option[Int], pageSize: Option[Int]): RpcRequest = {
    val (num, size) = (pageNum.getOrElse(ConfigUtil.getDefaultPageNum), pageSize.getOrElse(ConfigUtil.getDefaultPageSize))
    new RpcRequest(num, size)
  }

  def toPageJsValue(data: JsObject, totalCount: Int, pageNum: Option[Int], pageSize: Option[Int]): JsValue = {
    data ++ Json.obj("total_count" -> totalCount, "page_num" -> pageNum.getOrElse(1).toInt, "page_size" -> pageSize.getOrElse(totalCount).toInt)
  }

  val sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") //等价于now.toLocaleString()

}

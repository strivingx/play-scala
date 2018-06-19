package models

import ai.x.play.json.Jsonx
import models.Tables.TUserRow
import play.api.libs.json.Json
import utils.PageUtil.PageInfo

package object cases {

  case class RpcRequest(pageNum: Int = 1,
                        pageSize: Int = 10,
                        is_list: Boolean = true)

  case class User(user_id: String,
                  username: String,
                  password: String,
                  mail: Option[String] = None,
                  gender: Option[Boolean] = None,
                  age: Option[Int] = None)

  case class BookRequest(pageNum: Int = 1, pageSize: Int = 10, is_list: Boolean = true)

  case class Book(bookId: String, name: String)

  case class BookListInfo(bookId: String, name: String)

  implicit val userFormat = Json.format[User]

  implicit val bookFormat = Json.format[Book]
  implicit val userListInfoFormat = Json.format[BookListInfo]

  implicit def row2User(row: TUserRow) = new User("", row.username, row.password)

  implicit def user2ROw(user: User) = new TUserRow(0, user.username, user.password)

  implicit def userSimpleInfo2ROw(user: User) = new TUserRow(0, user.username, user.password)

  implicit val PageInfoFormats = Jsonx.formatCaseClassUseDefaults[PageInfo]

}

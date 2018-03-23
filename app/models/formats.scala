package models

import models.Tables.TUserRow
import models.cases._
import play.api.libs.json.Json

object formats {
  implicit val userFormat = Json.format[User]
  implicit val userSimpleInfoFormat = Json.format[UserSimpleInfo]

  implicit def row2User(row: TUserRow) = new User(row.username, row.password)

  implicit def row2UserSimpleInfo(row: TUserRow) = new UserSimpleInfo(row.username)

  implicit def user2ROw(user: User) = new TUserRow(0, user.username, user.password)

  implicit def userSimpleInfo2ROw(user: User) = new TUserRow(0, user.username, user.password)

}

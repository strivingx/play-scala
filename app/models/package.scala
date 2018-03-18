package models
import models.Tables.TUserRow
import play.api.libs.json.Json

package object formats {
  implicit val userFormat = Json.format[User]

  implicit def row2User(row: TUserRow) = new User(row.id.toString, row.username, row.password)
  implicit def user2ROw(user: User) = new TUserRow(0, user.userId, user.username, user.password)

}

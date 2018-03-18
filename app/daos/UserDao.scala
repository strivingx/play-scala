package daos

import java.util

import models.Tables._
import models.User
import models.formats._
import slick.jdbc.MySQLProfile.api._
import utils.DBUtil.UserDB._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class UserDao {

  def insertUser(user: User) = {
    db.run(TUser.map(p => (p.username, p.password)).forceInsert((user.username, user.password)))
  }

  def updateUser(user: User) = {
    db.run(TUser.filter(_.username === user.username)
      .map(p => (p.username, p.username))
      .update((user.username, user.username)))
  }

  def deleteUser(username: String): Future[Int] = {
    db.run(TUser.filter(_.username === username).delete)
  }

  def getUserId(username: String): Future[util.List[User]] = {
    db.run(TUser.filter { user =>
      user.username === username
    }.to[List].result).map(_.map(row2User))
  }

  def getUsers(): Future[util.List[User]] = {
    db.run(TUser.to[List].result).map(_.map(row2User))
  }

  /* def getUsersByKeyword(keyword: String): Future[util.List[User]] = {
       db.run(TUser.filter { user => user.name === keyword || user.urn.like(s"%$keyword%")
       }.to[List].result).map(_.map(rowToUser))
   }*/

}

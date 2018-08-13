package dao

import models.Tables._
import models.cases._
import slick.jdbc.MySQLProfile.api._
import utils.DatabaseProvider
import utils.PageUtil.PageQueryInfo

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
class UserDao(provider: DatabaseProvider) {

  val db = provider.getDatabase()
  def insertUser(user: User): Future[Int] = {
    db.run(TUser.map(p => (p.username, p.password)).forceInsert((user.username, user.password)))
  }

  def updateUser(user: User): Future[Int] = {
    db.run(TUser.filter(_.username === user.username)
            .map(p => (p.username, p.username))
            .update((user.username, user.username)))
  }

  def deleteUser(username: String): Future[Int] = {
    db.run(TUser.filter(_.username === username).delete)
  }

  def getUserByUsername(username: String): Future[User] = {
    db.run(TUser.filter { user =>
      user.username === username
    }.to[List].result.head).map(row2User)
  }

  def getUserQuery() = {
    val query = TUser.subquery
    query
  }

  def getUsersByRequest()(implicit info: PageQueryInfo = PageQueryInfo()): Future[List[User]] = {
    var query = getUserQuery()
    if (info.offset > 0) query = query.drop(info.offset)
    if (info.pageSize > 0) query = query.take(info.pageSize)
    db.run(getUserQuery().to[List].result).map(_.map(row2User))
  }

  def getUserCountByRequest(): Future[Int] = {
    db.run(getUserQuery.size.result)
  }

}

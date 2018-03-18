package daos

import java.util
import scala.concurrent.Future
import utils.DBUtil.UserDB._
import models.Tables._
import models.User
import models.formats._
import slick.jdbc.MySQLProfile.api._
import scala.collection.JavaConversions._
/**
  * Created by yyy on 18-1-29.
  */
class UserDao {

    def insertUser(user: User) = {
        db.run(TUser += user)
    }

    def updateUser(user: User) = {
        db.run(TUser.filter(_.userId === user.userId)
                .map(p => (p.username, p.username))
                .update((user.username, user.username)))
    }

  def deleteUser(userId:String ):Future[Int] = {
    db.run(TUser.filter(_.userId === userId).delete)
  }

    def getUserId(userId: String): Future[util.List[User]] = {
        db.run(TUser.filter { user =>
            user.userId === userId
        }.to[List].result).map(_.map(row2User))
    }

    def getUsers(): Future[util.List[User]]: Future[java.util.List[User]] = {
        db.run(TUser.to[List].result).map(_.map(row2User))
    }

   /* def getUsersByKeyword(keyword: String): Future[util.List[User]] = {
        db.run(TUser.filter { user => user.name === keyword || user.urn.like(s"%$keyword%")
        }.to[List].result).map(_.map(rowToUser))
    }*/

}

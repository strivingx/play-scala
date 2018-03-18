package services

import daos.UserDao
import models.User

import scala.concurrent.Future

class UserService   {
  private val dao = new UserDao
  def createUser(user: User) = {
    dao.insertUser(user)
  }

  def updateUser(user: User) = {

  }

  def deleteUser(userId: String):Future[Int] = {
    dao.deleteUser(userId)
  }


  def getUser(user: User) = {

  }

  def getUsers(): Future[java.util.List[User]] = {
    dao.getUsers()
  }

}
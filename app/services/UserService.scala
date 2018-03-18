package services

import models.User

import scala.concurrent.Future

trait UserService {

  def createUser(user: User): Future[Int]

  def updateUser(user: User): Future[Int]

  def deleteUser(userId: String): Future[Int]

  def getUserByUsername(username: String) : Future[User]

  def getUsers(): Future[java.util.List[User]]

}
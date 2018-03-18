package services

import models.User
import models.cases.UserRequest

import scala.concurrent.Future

trait UserService {

  def createUser(user: User): Future[Int]

  def updateUser(user: User): Future[Int]

  def deleteUser(userId: String): Future[Int]

  def getUserByUsername(username: String): Future[User]

  def getUsersByRequest(request: UserRequest): Future[java.util.List[User]]

  def getUserCountByRequest(request: UserRequest): Future[Int]

}
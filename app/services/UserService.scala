package services

import models.cases.{User, UserRequest, UserSimpleInfo}

import scala.concurrent.Future

trait UserService {

  def createUser(user: User): Future[Int]

  def updateUser(user: User): Future[Int]

  def deleteUser(userId: String): Future[Int]

  def getUserByUsername(username: String): Future[User]

  def getUsersByRequest(request: UserRequest): Future[java.util.List[User]]

  def getUserSimpleInfosByRequest(request: UserRequest): Future[java.util.List[UserSimpleInfo]]

  def getUserCountByRequest(request: UserRequest): Future[Int]

}
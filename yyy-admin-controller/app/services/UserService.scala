package services

import models.cases._
import utils.PageUtil.PageInfo

import scala.concurrent.Future

trait UserService {

  def saveUser(user: User): Future[String]

  def deleteUser(userId: String): Future[Boolean]

  def getUserByUsername(username: String): Future[User]

  def getUserPageInfoByRequest(request: RpcRequest): Future[PageInfo]

}
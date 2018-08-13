package services

import models.cases._
import org.apache.commons.lang3.StringUtils
import utils.DatabaseProvider
import utils.PageUtil._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class UserServiceImpl(provider: DatabaseProvider) extends BaseServiceImpl(provider) with UserService {

  override def saveUser(user: User): Future[String] = {
    if (StringUtils.isBlank(user.user_id)) {
      val userId = "id"
      userDao.insertUser(user).map(_ => "")
    } else {
      userDao.updateUser(user).map(_ => "")
    }
  }

  override def deleteUser(userId: String): Future[Boolean] = {
    userDao.deleteUser(userId).map(_ > 0)
  }

  override def getUserByUsername(username: String) = {
    userDao.getUserByUsername(username)
  }

  override def getUserPageInfoByRequest(request: RpcRequest): Future[PageInfo] = {
    implicit val pageQueryInfo = toPageQueryInfo(request.pageNum, request.pageSize)
    getPageInfoByRequest[RpcRequest, User](request, getUsersByRequest, getUserCountByRequest)
  }

  private def getUsersByRequest(request: RpcRequest)(implicit info: PageQueryInfo): Future[List[User]] = {
    userDao.getUsersByRequest()
  }

  private def getUserCountByRequest(request: RpcRequest)(implicit info: PageQueryInfo): Future[Long] = {
    userDao.getUserCountByRequest().map(_.toLong)
  }

}
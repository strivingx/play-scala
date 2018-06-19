package services

import dao.UserDao
import models.cases._
import org.apache.commons.lang3.StringUtils
import utils.PageUtil._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class UserServiceImpl extends UserService {
  private val dao = new UserDao

  override def saveUser(user: User): Future[String] = {
    if (StringUtils.isBlank(user.user_id)) {
      val userId = "id"
      dao.insertUser(user).map(_ => "")
    } else {
      dao.updateUser(user).map(_ => "")
    }
  }

  override def deleteUser(userId: String): Future[Boolean] = {
    dao.deleteUser(userId).map(_ > 0)
  }

  override def getUserByUsername(username: String) = {
    dao.getUserByUsername(username)
  }

  override def getUserPageInfoByRequest(request: RpcRequest): Future[PageInfo] = {
    implicit val pageQueryInfo = toPageQueryInfo(request.pageNum, request.pageSize)
    getPageInfoByRequest[RpcRequest, User](request, getUsersByRequest, getUserCountByRequest)
  }

  private def getUsersByRequest(request: RpcRequest)(implicit info: PageQueryInfo): Future[List[User]] = {
    dao.getUsersByRequest()
  }

  private def getUserCountByRequest(request: RpcRequest)(implicit info: PageQueryInfo): Future[Long] = {
    dao.getUserCountByRequest().map(_.toLong)
  }

}
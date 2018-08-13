package services

import dao.UserDao
import models.cases._
import org.apache.commons.lang3.StringUtils
import utils.DatabaseProvider
import utils.PageUtil._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class BaseServiceImpl(provider: DatabaseProvider) {
  protected val userDao = new UserDao(provider)

  protected def generateId(): String = {
    ""
  }
}
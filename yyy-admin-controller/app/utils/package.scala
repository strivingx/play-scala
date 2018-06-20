package utils

import commons.Logging
import models.cases.User
import play.api.mvc.Results

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.reflect.ClassTag

object PageUtil extends Logging with Results{
    case class PageQueryInfo(pageNum: Int = 0, pageSize: Int = 0, offset: Int = 0)

    def toPageQueryInfo(pageNum: Int, pageSize: Int): PageQueryInfo = {
        val page = if (pageNum <= 0) 1 else pageNum
        val size = if (pageSize <= 0) 0 else pageSize
        val offset = (page - 1) * pageSize
        PageQueryInfo(page, size, offset)
    }

    def toPageInfo[T](data: List[T], info: PageQueryInfo, totalCount: Long)(implicit tag: ClassTag[T]): PageInfo = {
        val count = if (totalCount == 0) data.size.toLong else totalCount
        val pageInfo = PageInfo(info.pageNum, info.pageSize, count)
        tag.runtimeClass.getSimpleName match {
            case "User" => pageInfo.users = data.asInstanceOf[List[User]]
            case typeName => throw new IllegalArgumentException(s"toPageInfo暂不支持类型$typeName")
        }
        pageInfo
    }

    case class PageInfo(page_num: Int = 0,
                        page_size : Int = 0,
                        total_count: Long = 0,
                        var users: List[User] = Nil)

   /* def toPageInfo[T](data: java.util.List[T], pageNum: Int, pageSize: Int, totalCount: Long)(implicit tag: ClassTag[T]): PageInfo[T] = {
        val totalPage = if (pageSize <= 0) 1 else Math.ceil(totalCount * 1.0 / pageSize).toInt
        val info = new PageInfo()
                .setPageNum(pageNum)
                .setPageSize(pageSize)
                .setCount(totalCount)
                .setTotalPage(totalPage)
        tag.runtimeClass.getSimpleName match {
            /*case "User" => info.setSkills(data.asInstanceOf[java.util.List[Skill]])
            case "MIOTDevice" => info.setDevices(data.asInstanceOf[java.util.List[MIOTDevice]])
            case "MIOTDeviceSpec" => info.setSpecs(data.asInstanceOf[java.util.List[MIOTDeviceSpec]])
            case "SkillFeedback" => info.setSkillFeedbacks(data.asInstanceOf[java.util.List[SkillFeedback]])
            case "SkillRating" => info.setSkillRatings(data.asInstanceOf[java.util.List[SkillRating]])
            case "DeveloperInfo" => info.setDevelopers(data.asInstanceOf[java.util.List[DeveloperInfo]])*/
            case typeName => throw new IllegalArgumentException(s"toPageInfo暂不支持类型$typeName")
        }
        info
    }*/

    def getPageInfoByRequest[R, E](request: R, getResultsByRequest: R => Future[List[E]], getCountByRequest: R => Future[Long])(implicit info: PageQueryInfo, tag: ClassTag[E]): Future[PageInfo] = {
        for {
            entities <- getResultsByRequest(request)
            count: Long <- (if (info.pageSize == 0) {
                Future.successful(entities.size.toLong)
            } else {
                getCountByRequest(request).map(_.toLong)
            })
        } yield {
            toPageInfo[E](entities, info, count)
        }
    }

}

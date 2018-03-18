package controllers

import com.xiaomi.ai.api.StatusCode
import com.xiaomi.ai.api.formats._
import com.xiaomi.ai.open.thrift._
import com.xiaomi.common.play.swift.SwiftConverter
import controllers.SkillStoreController
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{BeforeAndAfter, BeforeAndAfterAll, Matchers}
import org.scalatestplus.play.PlaySpec
import play.api.libs.json._

import scala.collection.JavaConversions._

class UserControllerSpec extends PlaySpec with Matchers with BeforeAndAfterAll with MockitoSugar{

    val controller = new UserController()

    "test register user" should "run ok" in{
    }

    "test delete user" should "run ok" {
    }

    "test get users" should "run ok" {
    }

    "test getSkillByName" should "run ok" {
    }

}
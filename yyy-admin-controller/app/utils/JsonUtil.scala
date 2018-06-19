package utils

import java.util

import play.api.libs.json.{JsObject, JsValue, Json}

import scala.reflect.ClassTag
import scala.collection.JavaConversions._
object JsonUtil {

  def toSeqJsValueOpt(json: String): Option[List[JsValue]] = {
    try {
      Json.parse(json).asOpt[List[JsValue]]
    } catch {
      case e: Exception => None
    }
  }

  def jsonToList[T: ClassTag](json: String)(implicit tag: ClassTag[T]): Option[util.List[T]] = {
    val clazz = tag.runtimeClass
    clazz.getSimpleName match {
      case "String" => toSeqJsValueOpt(json).map(_.map(_.as[String].asInstanceOf[T]))
      case "long" => toSeqJsValueOpt(json).map(_.map(_.as[Long].asInstanceOf[T]))
      case "int" => toSeqJsValueOpt(json).map(_.map(_.as[Int].asInstanceOf[T]))
      case "double" => toSeqJsValueOpt(json).map(_.map(_.as[Double].asInstanceOf[T]))
      case "short" => toSeqJsValueOpt(json).map(_.map(_.as[Short].asInstanceOf[T]))
      case "boolean" => toSeqJsValueOpt(json).map(_.map(_.as[Boolean].asInstanceOf[T]))
      case "List" =>  throw new Exception(s"暂不支持$clazz")
      case _ =>
        if (clazz.getSuperclass.getCanonicalName == "java.lang.Enum") {
          val enumMethod = tag.runtimeClass.getMethods.filter(_.getName.equals("findByValue"))(0)
          toSeqJsValueOpt(json).map(_.map(_.as[Int]).map(value => enumMethod.invoke(null, new Integer(value)).asInstanceOf[T]))
        } else {
          throw new Exception(s"暂不支持$clazz")
        }
    }
  }

}

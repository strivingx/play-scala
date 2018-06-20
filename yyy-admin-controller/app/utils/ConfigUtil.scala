package utils

import com.typesafe.config.ConfigFactory

object ConfigUtil {
  val config = ConfigFactory.load()

  def getDefaultPageNum(): Int = config.getInt("page.default.num")

  def getDefaultPageSize(): Int = config.getInt("page.default.size")

}

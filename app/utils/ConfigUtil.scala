package utils

import com.typesafe.config.{Config, ConfigFactory}
import slick.jdbc.JdbcBackend.backend.Database

/**
  * Created by yyy on 18-1-29.
  */
object ConfigUtil {
    val config = ConfigFactory.load()

    def getDefaultPageNum(): Int = config.getInt("page.default.num")

    def getDefaultPageSize(): Int = config.getInt("page.default.size")

}

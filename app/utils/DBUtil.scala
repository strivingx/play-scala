package utils

import com.typesafe.config.{Config, ConfigFactory}
import slick.jdbc.JdbcBackend.backend.Database

/**
  * Created by yyy on 18-1-29.
  */
object DBUtil {
  val config: Config = ConfigFactory.load()

  private def getDatabase(dbName: String) = {
    val urlConf = config.getString(s"database.${dbName}.url")
    val driverConf = config.getString(s"database.${dbName}.driver")
    val userConf = config.getString(s"database.${dbName}.user")
    val passwordConf = config.getString(s"database.${dbName}.password")
    Database.forURL(url = urlConf, driver = driverConf, user = userConf, password = passwordConf)
  }

  object UserDB {
    val db = getDatabase("test")

    override def finalize() {
      db.close()
      super.finalize()
    }
  }

}

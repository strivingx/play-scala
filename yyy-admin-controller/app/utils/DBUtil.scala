package utils

import com.google.inject.Guice
import com.typesafe.config.ConfigFactory
import modules.MyModule
import slick.jdbc.JdbcBackend.backend.Database

trait DatabaseProvider {
  private val config = ConfigFactory.load()
  protected def getDatabase(dbName: String) = {
    val urlConf = config.getString(s"database.${dbName}.url")
    val driverConf = config.getString(s"database.${dbName}.driver")
    val userConf = config.getString(s"database.${dbName}.user")
    val passwordConf = config.getString(s"database.${dbName}.password")
    Database.forURL(url = urlConf, driver = driverConf, user = userConf, password = passwordConf)
  }

  def getDatabase(): Database
}

class DatabaseProviderImpl extends DatabaseProvider {
  def getDatabase(): Database = {
    // Database.forConfig("h2mem1")
    getDatabase("test")
    Database.forURL(url = s"jdbc:h2:mem:test1", driver = "org.h2.Driver")

  }
}
/**
  * Created by yyy on 18-1-29.
  */
object DBUtil extends App {

  val db = Guice.createInjector(new MyModule()).getInstance(classOf[DatabaseProvider]).getDatabase()

  override def finalize() {
    db.close()
    super.finalize()
  }

  println(db)
}

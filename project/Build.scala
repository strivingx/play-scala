import com.typesafe.sbt.packager.archetypes.JavaAppKeys
import play.sbt.PlayImport.{ws, _}
import sbt.Keys._
import sbt._


object Build extends JavaAppKeys {

  object Deps {

    val slick_version = "3.2.1"
    libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "2.0.0" % Test
    val slick = Seq(
      "com.typesafe.slick" %% "slick" % slick_version,
      "com.typesafe.slick" %% "slick-codegen" % slick_version
    )
    // https://mvnrepository.com/artifact/mysql/mysql-connector-java
    val mysql = "mysql" % "mysql-connector-java" % "5.1.27"
    val mailer = Seq("com.typesafe.play" %% "play-mailer" % "6.0.1",
      "com.typesafe.play" %% "play-mailer-guice" % "6.0.1"
    )
    val mongodb = "org.mongodb" %% "mongo-java-driver" % "3.6.3"
    val redis = "redis.clients" % "jedis" % "2.9.0"
  }

  lazy val playScala = Seq(
    jdbc,
    cache,
    ws,
    Deps.slick,
    Deps.mysql,
    Deps.mongodb,
    Deps.redis,
    Deps.mailer
  )
}

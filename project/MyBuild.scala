import com.typesafe.sbt.packager.archetypes.JavaAppKeys
import play.routes.compiler.InjectedRoutesGenerator
import play.sbt.PlayImport.{ws, _}
import play.sbt.PlayScala
import play.sbt.routes.RoutesKeys.routesGenerator
import sbt.Keys._
import sbt._


object MyBuild extends Build with JavaAppKeys {

  object Deps {
    val slick_version = "3.2.1"
    val scala_test = "org.scalatestplus.play" %% "scalatestplus-play" % "2.0.0" % Test
    val zookeeper = "org.apache.zookeeper" % "zookeeper" % "3.5.3-beta"
    val mysql = "mysql" % "mysql-connector-java" % "5.1.26"
    val mongodb = "org.mongodb" %% "mongo-java-driver" % "3.6.3"
    val redis = "redis.clients" % "jedis" % "2.9.0"
    val play_json = "ai.x" %% "play-json-extensions" % "0.10.0"
    val akka = "com.typesafe.akka" % "akka-actor_2.11" % "2.5.3"

    val play_mailer = Seq("com.typesafe.play" %% "play-mailer" % "6.0.1",
      "com.typesafe.play" %% "play-mailer-guice" % "6.0.1"
    )
    val slick = Seq(
      "com.typesafe.slick" %% "slick" % slick_version,
      "com.typesafe.slick" %% "slick-codegen" % slick_version
    )
    // https://mvnrepository.com/artifact/org.apache.thrift/libthrift
    val thrift = "org.apache.thrift" % "libthrift" % "0.11.0"
    // https://mvnrepository.com/artifact/com.facebook.nifty/nifty-core
    val nifty = "com.facebook.nifty" % "nifty-core" % "0.23.0"

  }

  lazy val root = (project in file(".")).enablePlugins(PlayScala)
        .settings(
          libraryDependencies ++= Seq(
            jdbc,
            cache,
            ws,
            Deps.mysql,
//            Deps.mongodb,
            Deps.redis,
            Deps.scala_test,
            Deps.akka,
            Deps.play_json,
            Deps.zookeeper,
            Deps.thrift,
            Deps.nifty
          ) ++ Deps.slick
        )

  def playProject(projectName: String) = baseProject(projectName)
    .enablePlugins(PlayScala)
    .settings(
      routesGenerator := InjectedRoutesGenerator
    )

  def baseProject(projectName: String) = Project(projectName, file(projectName))
    .settings(
      name := projectName,
      libraryDependencies ++= excludedDeps(
      )
    )

  def excludedDeps(deps: ModuleID*) = {
    deps.toSeq
    //        _.exclude("org.jboss.netty", "netty")
  }
}

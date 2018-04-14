name := """play-scala"""
version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.11"

libraryDependencies += jdbc
libraryDependencies += cache
libraryDependencies += ws
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "2.0.0" % Test
libraryDependencies ++= Seq(
  "com.typesafe.slick" %% "slick" % "3.2.1",
  "com.typesafe.slick" %% "slick-codegen" % "3.2.1"
)
libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.27"
libraryDependencies += "com.typesafe.play" %% "play-mailer" % "6.0.1"
libraryDependencies += "com.typesafe.play" %% "play-mailer-guice" % "6.0.1"
libraryDependencies += "org.apache.zookeeper" % "zookeeper" % "3.5.3-beta"
libraryDependencies += "ai.x" %% "play-json-extensions" % "0.10.0"
libraryDependencies += "com.typesafe.akka" % "akka-actor_2.11" % "2.5.3"
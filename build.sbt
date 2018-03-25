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
// https://mvnrepository.com/artifact/mysql/mysql-connector-java
libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.27"
libraryDependencies += "com.typesafe.play" %% "play-mailer" % "6.0.1"
libraryDependencies += "com.typesafe.play" %% "play-mailer-guice" % "6.0.1"



// https://mvnrepository.com/artifact/org.apache.spark/spark-core
libraryDependencies += "org.apache.spark" %% "spark-core" % "2.3.0"
// https://mvnrepository.com/artifact/org.mongodb/mongo-java-driver
// libraryDependencies += "org.mongodb" % "mongo-java-driver" % "3.6.3"

// https://mvnrepository.com/artifact/redis.clients/jedis
// libraryDependencies += "redis.clients" % "jedis" % "2.9.0"

dependencyOverrides += "com.fasterxml.jackson.core" % "jackson-core" % "2.9.4"
dependencyOverrides += "com.fasterxml.jackson.core" % "jackson-databind" % "2.9.4"

// https://mvnrepository.com/artifact/com.fasterxml/jackson-module-scala
dependencyOverrides += "com.fasterxml" % "jackson-module-scala" % "1.9.3"

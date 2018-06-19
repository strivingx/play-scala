package utils

import slick.codegen.SourceCodeGenerator

/**
  * Created by yyy on 18-1-30.
  */
object SlickCodeGeneratorUtil extends App {
  val slickDriver = "slick.jdbc.MySQLProfile"
  val jdbcDriver = "com.mysql.jdbc.Driver"
  val url = "jdbc:mysql://localhost:3306/test?searchpath=public"
  //     val url = "jdbc:h2:mem:test;INIT=runscript from 'study/main/sql/create1.sql'" //加载sql文件

  val user = "root"
  val password = "root"
  val outputFolder = "d:/"
  val pkg = "models"
  SourceCodeGenerator.main(
    Array(slickDriver,
      jdbcDriver,
      url,
      outputFolder,
      pkg,
      user,
      password
    )
  )
}


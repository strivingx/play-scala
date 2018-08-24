package test.util

import slick.codegen.SourceCodeGenerator

/**
  * Created by yyy on 18-1-30.
  */
object SlickCodeGeneratorUtil2 extends App {
    val slickDriver = "slick.jdbc.MySQLProfile"
    val jdbcDriver = "com.mysql.jdbc.Driver"
    val url = "jdbc:mysql://10.38.161.94:3306/skill_store?searchpath=public"
    val user = "root"
    val password = "69e8d4d3e71227fc9e466267ec6e6a81"
    val outputFolder = "/home/yuyayun/Downloads"
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


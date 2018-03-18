package utils

import slick.codegen.SourceCodeGenerator

/**
  * Created by yyy on 18-1-30.
  */
object SlickCodeGeneratorUtil extends App {
    val slickDriver = "slick.jdbc.MySQLProfile"
    val jdbcDriver = "com.mysql.jdbc.Driver"
    val url = "jdbc:mysql://localhost:3306/test?searchpath=public"
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


package test

import java.sql.{DriverManager, ResultSet}

object TestH2 extends App {

    def printlnRS(rs: ResultSet, msg: String = ""): Unit = {
        println(msg)
        while (rs.next) {
            val count = rs.getMetaData.getColumnCount
            System.out.println((1 to count).map(rs.getObject).mkString("; "))
        }
        println(s"------end $msg")
    }

    def getConnection() = {
        Class.forName("org.h2.Driver")
        DriverManager.getConnection("jdbc:h2:tcp://localhost/mem:test2", "sa", "")
    }

    val conn = getConnection()
    val stmt = conn.createStatement
    stmt.executeUpdate("CREATE SCHEMA `SKILL_STORE` ")
    stmt.execute("USE `SKILL_STORE` ")
    printlnRS(stmt.executeQuery("show tables;"), "show tables")

    stmt.executeUpdate("CREATE TABLE TEST_MEM(ID INT PRIMARY KEY,NAME VARCHAR(255));")
    stmt.executeUpdate("INSERT INTO TEST_MEM VALUES(1, 'Hello_Mem');")
    stmt.executeUpdate("DELETE TEST_MEM FROM TEST_MEM WHERE ID = 1")
    stmt.executeUpdate("CREATE SCHEMA `SKILL_STORE1` ")
    // stmt.execute("USE `SKILL_STORE1` ")
    printlnRS(stmt.executeQuery("show tables;"), "show tables")
    printlnRS(stmt.executeQuery("SELECT * FROM TEST_MEM;"), "select")
    printlnRS(stmt.executeQuery("show databases;"), "show databases")
    conn.close()
}

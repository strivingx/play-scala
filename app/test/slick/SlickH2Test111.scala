package test.slick

// Use H2Driver to connect to an H2 database
import slick.dbio.{DBIOAction, NoStream}
import slick.jdbc.H2Profile.api._
import test.slick.SlickTable.{coffees, _}
import test.slick.SlickUtil.db

import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

object SlickH2Test111 extends App {
    val db = SlickUtil.db

    class Coffees(tag: Tag) extends Table[(String, Double)](tag, "COFFEES") {
        def name = column[String]("COF_NAME", O.PrimaryKey)

        def price = column[Double]("PRICE")

        override def * = (name, price)
    }

    val coffees = TableQuery[Coffees]

    val setup = DBIO.seq(
        // Create the tables, including primary and foreign keys
        (coffees.schema).create,
        coffees ++= Seq(
            ("Colombian", 10.1),
            ("French_Roast", 4.9),
            ("Espresso", 15.0)
        )
    )
    run(setup)

    coffees.map(_.name)
    coffees.filter(_.price < 10.0)

    Await.result(db.run(coffees.to[List].result), Duration.Inf).foreach(println)

    println(Await.result(db.run(coffees.to[List].result), Duration.Inf))
    printResult(coffees.to[List].result)
    println(run(coffees.to[List].result))

    def printResult[R](a: DBIOAction[R, NoStream, Nothing]) = {
        println(run(a))
    }

    def run[R](a: DBIOAction[R, NoStream, Nothing]) = {
        Await.result(db.run(a), Duration.Inf)
    }

    val limit = 10.0

    sql"select COF_NAME from COFFEES where PRICE < $limit".as[String]

}

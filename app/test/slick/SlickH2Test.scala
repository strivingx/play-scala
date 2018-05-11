package test.slick

// Use H2Driver to connect to an H2 database
import slick.jdbc.H2Profile.api._

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import test.slick.SlickTable._
object SlickH2Test extends App {
    val db = SlickUtil.db
    SlickUtil.init
    try {
        println("Coffees:")
        Await.result(db.run(coffees.result).map(_.foreach {
            case (name, supID, price, sales, total) => println("  " + name + "\t" + supID + "\t" + price + "\t" + sales + "\t" + total)
        }), Duration.Inf)

        val q1 = for (c <- coffees)
            yield LiteralColumn("  ") ++ c.name ++ "\t" ++ c.supID.asColumnOf[String] ++
                    "\t" ++ c.price.asColumnOf[String] ++ "\t" ++ c.sales.asColumnOf[String] ++
                    "\t" ++ c.total.asColumnOf[String]
        // The first string constant needs to be lifted manually to a LiteralColumn
        // so that the proper ++ operator is found

        // Equivalent SQL code:
        // select '  ' || COF_NAME || '\t' || SUP_ID || '\t' || PRICE || '\t' SALES || '\t' TOTAL from COFFEES

        db.stream(q1.result).foreach(println)

        // Perform a join to retrieve coffee names and supplier names for
        // all coffees costing less than $9.00
        val q2 = for {
            c <- coffees if c.price < 9.0
            s <- suppliers if s.id === c.supID
        } yield (c.name, s.name)

        // Await.result(q2)
        // Equivalent SQL code:
        // select c.COF_NAME, s.SUP_NAME from COFFEES c, SUPPLIERS s where c.PRICE < 9.0 and s.SUP_ID = c.SUP_ID

        val q3 = for {
            c <- coffees if c.price < 9.0
            s <- c.supplier
        } yield (c.name, s.name)
        println(q3)
        // Equivalent SQL code:
        // select c.COF_NAME, s.SUP_NAME from COFFEES c, SUPPLIERS s where c.PRICE < 9.0 and s.SUP_ID = c.SUP_ID
    } finally db.close
}

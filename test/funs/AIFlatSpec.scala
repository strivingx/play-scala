package funs

import org.scalatest.FlatSpec

class AIFlatSpec extends FlatSpec {

  "scala test" should "be ok" in {
    println(Option(Nil))
    println(Option(Nil).filter(!_.isEmpty))
  }

}
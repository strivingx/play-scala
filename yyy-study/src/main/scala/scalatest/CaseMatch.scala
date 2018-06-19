package scalatest

object CaseMatch extends  App {
  val hi = Array("hi", "hello", "world")
  hi match {
    case Array("hi", _*) => println("Ok")
    case Array("hi", "world", _) => println("Wrong")
    case _ => println("None")
  }

  def getType(a: Any): Unit = {
    a match {
      case _: Array[Char] => println("Array [Char]")
      case _: Int => println("Int")
      case _ => println("None")
    }
  }

  getType(333)
}

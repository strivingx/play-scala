package test


object Main extends App {
  println(1111)
  var arr = new Array[Int](5)
  arr = Array(5, 5, 4, 5)
  arr(3) = 6
  val buffer = arr.toBuffer
  buffer += 7
  buffer ++= arr
  arr.foreach(println)
  buffer.foreach(println)
  for (i <- buffer)
    print(i)
  println
  val map = Map(1 -> 2, 3 -> 5, 4 -> 7, (5, 6))
  //map(5)=10
  println(map)
  println(map(3))
  println(map.get(4))
  println(map.getOrElse(6, 0))

  val g = (2, 4, 6, 8, 0)
  println(g._1 + g._3)

  val c = 'H'
  println(c)

  val mainObject = new MainObject
  println(mainObject.classVal)
  println(mainObject.defaultVal)

  val getNum: PartialFunction[Int, String] = {
    case 1 => "one"
    case 2 => "two"
  }
  println(getNum.isDefinedAt(1))
  println(getNum.isDefinedAt(3))

  /*react {
    case (name:String , age :Int) =>println(s"$name   ,   $age")
    case (msg:String) =>println(s"msg")
  }*/

  def printContent[T](a:T)={
    println(a)
  }
def printString = printContent[String] _
  printContent[String]("aaaaa")
  printContent("bbbbb")
  printString("ccccc")
}
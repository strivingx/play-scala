package test.akka

object TestActor extends  App{
  def sum(n: Int): Int = {
    if (n > 1) sum(n - 1) + n else n
  }

  def sum1(n: Int) = {
    (n to n + 10).foreach(i => println(sum(i)))
  }


  sum1(100)
  println("---------------------------------")
}

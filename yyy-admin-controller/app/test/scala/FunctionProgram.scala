package test.scala

object FunctionProgram extends App {
  def fun(a: Int): Int = {
    a * a +1
  }

  def sum1(a: Int, b: Int): Int = {
    if (a > b) 0    else fun(a) + sum1(a + 1, b)
  }

 /* def sum(f: (Int, Int) => Int, a: Int, b: Int): Int = {
    if (a > b) 0
    else f(a, a + 1) + sum1(a + 1, b)
  }*/
  def sum(f: (Int) => Int, a: Int, b: Int): Int = {
    if (a > b) 0
    else f(a) + sum(f, a + 1, b)
  }
  println(sum1(3, 7))

  def fun1(a: Int, b: Int): Int = {
    a * b
  }

  println(sum((x:Int)=> x + x, 3, 7))

  def mul(a:Int)=(b:Int)=>a*b
  println(mul(3)(5))

}
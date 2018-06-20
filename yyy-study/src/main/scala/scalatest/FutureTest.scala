package scalatest

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

object FutureTest extends App {
  val start = System.currentTimeMillis()
  val mFuture = Future {
    Thread sleep 1000
    "result"
  }
  val result = Await result(mFuture, Duration.create("3 seconds"))
  println(result)
  println(s"cost time: ${System.currentTimeMillis() - start}")
}
package test

import akka.actor.{Actor, ActorSystem, Props}

class ActorTest extends Actor {
  def receive = {
    case "hello" => println("您好！")
    case _ => println("您是?")
  }
}

object ActorTest extends App {
  val system = ActorSystem()
  // 缺省的Actor构造函数
  val helloActor = system.actorOf(Props[ActorTest], name = "helloActor")
  helloActor ! "hello"
  helloActor ! "喂"
}

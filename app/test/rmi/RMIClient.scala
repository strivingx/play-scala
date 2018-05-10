package test.rmi

import java.rmi.Naming
import java.rmi.registry.LocateRegistry

object RMIClient extends App {
  val port = 9999
  val url = "rmi://localhost:9999/test.rmi.RMIServiceImpl"
  val service = Naming.lookup(url).asInstanceOf[RMIService]
  service.sayHello()
}

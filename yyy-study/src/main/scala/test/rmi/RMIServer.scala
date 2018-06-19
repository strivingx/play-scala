package test.rmi

import java.rmi.Naming
import java.rmi.registry.LocateRegistry

object RMIServer extends App {
  val port = 9999
  val url = "rmi://localhost:9999/test.rmi.RMIServiceImpl"
  LocateRegistry.createRegistry(port)
  Naming.rebind(url, new RMIServiceImpl())
}

package test.rmi

import java.rmi.{Naming, Remote}
import java.rmi.registry.LocateRegistry

object RMIUtil {
  def publishService(remote: Remote, host: Int, port: Int): String = {
    val url = s"rmi://${host}:$port/${remote.getClass.getName}"
    LocateRegistry.createRegistry(port)
    Naming.rebind(url, new RMIServiceImpl())
    url
  }
}

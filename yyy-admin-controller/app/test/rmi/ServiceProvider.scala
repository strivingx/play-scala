package test.rmi

import java.rmi.registry.LocateRegistry
import java.rmi.{Naming, Remote}

import org.apache.commons.lang3.StringUtils

object ServiceProvider extends App {
  def publish(remote: Remote, host: Int, port: Int) = {
    val url = RMIUtil.publishService(remote, host, port)
    if (StringUtils.isNotBlank(url)) {
      val zk = ZookeeperUtil.connectZookeeper()
    }
    ""
  }
}

package test.rmi

import org.apache.zookeeper.{WatchedEvent, Watcher, ZooKeeper}

object ZookeeperUtil {
  def connectZookeeper(): ZooKeeper = {
    val watcher = new Watcher {
      override def process(event: WatchedEvent): Unit = println(s"haha ${event}")
    }
    new ZooKeeper(s"127.0.0.1:2181", 3000, watcher)
  }
}

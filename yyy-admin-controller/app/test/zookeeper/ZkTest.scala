package test.zookeeper

import org.apache.zookeeper.{WatchedEvent, Watcher, ZooKeeper}

/*
 ubuntu配置https://blog.csdn.net/Yan_Chou/article/details/53322429
 http://coolxing.iteye.com/blog/1871009
 编码https://www.cnblogs.com/ggjucheng/p/3370359.html
*/
object ZkTest extends App {

    val zk = new ZooKeeper(s"127.0.0.1:2181", 3000, watcher)

    val watcher = new Watcher {
        override def process(event: WatchedEvent): Unit = println(s"haha ${event}")
    }

    zk.close()

}

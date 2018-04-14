package test

import org.apache.zookeeper.ZooDefs.Ids
import org.apache.zookeeper.client.ZKClientConfig
import org.apache.zookeeper.{CreateMode, WatchedEvent, Watcher, ZooKeeper}

/*
 ubuntu配置https://blog.csdn.net/Yan_Chou/article/details/53322429
 http://coolxing.iteye.com/blog/1871009
 编码https://www.cnblogs.com/ggjucheng/p/3370359.html
*/
object ZkTest extends App {

    /*


    create /zk
    ls /

     set /zk "zktest"

 get /zk
delete /zk
     */

    def create(): Unit = {

    }

    def update() = {

    }

    def delete() = {

    }

    def get(): Unit = {

    }

    val watcher = new Watcher() {
        // 监控所有被触发的事件
        def process(event: WatchedEvent) {
            println("已经触发了" + event.getType() + "事件！")
        }
    }
    // 创建一个与服务器的连接
    val zk = new ZooKeeper("localhost:2181", 1000, watcher)
    // 创建一个目录节点
    zk.create("/testRootPath", "testRootData".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT)
    // 创建一个子目录节点
    zk.create("/testRootPath/testChildPathOne", "testChildDataOne".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT)
    // 创建另外一个子目录节点
    zk.create("/testRootPath/testChildPathTwo", "testChildDataTwo".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT)

    // 检查节点是否存在
    println("目录节点状态：[" + zk.exists("/testRootPath", true) + "]")
    println("目录节点状态：[" + zk.exists("/testRootPath/testChildPathTwo2", watcher) + "]")

    println(zk.getChildren("/testRootPath", true))
    // 为节点设置数据
    zk.setData("/testRootPath/testChildPathOne", "modifyChildDataOne".getBytes(), -1)
    println(new String(zk.getData("/testRootPath", false, null)))
    println(new String(zk.getData("/testRootPath/testChildPathOne", true, null)))

    // 删除节点
    zk.delete("/testRootPath/testChildPathTwo", -1)
    zk.delete("/testRootPath/testChildPathOne", -1)
    zk.delete("/testRootPath", -1)

    // 关闭连接
    zk.close()

}

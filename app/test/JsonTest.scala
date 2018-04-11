package test

import ai.x.play.json.Jsonx
import org.apache.zookeeper.client.ZKClientConfig
import play.api.libs.json.Json

/*
 ubuntu配置https://blog.csdn.net/Yan_Chou/article/details/53322429
 http://coolxing.iteye.com/blog/1871009
 编码https://www.cnblogs.com/ggjucheng/p/3370359.html
*/
object JsonTest extends App {

    case class TreeData(id: String, name: String, height: Long)

    case class TreeResponse(error: Int, message: String, data: Option[TreeData])

    implicit val treeDataFormats = Jsonx.formatCaseClassUseDefaults[TreeData]
    implicit val treeResponseFormats = Jsonx.formatCaseClassUseDefaults[TreeResponse]


    val json = Json.parse("""{"error":0,"message":"ok","data":{"id":"afJ4ttxLBmQL62ZvpUxJvg==","name":"","height":59}}""")
    println(json.as[TreeResponse])
}

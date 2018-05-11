package test1

import scala.io.Source

object Test extends App {

    object MyAction {
        final def apply(fun: String => String) = {
            print(fun("aaa"))
        }
    }

    val list = Source.fromFile("/home/yuyayun/Downloads/违禁词").getLines().filter(_.matches("[\\u4e00-\\u9fa5]{1,8}")) //.mkString("\n")
    println(s"[${list.mkString("\n")}]")

    MyAction { a =>
        a + "sss"
    }
}

package test.slf4j

import scala.collection.mutable

object Slf4jTest extends App {

    import org.slf4j.LoggerFactory

    // 类名.class
    val logger = LoggerFactory.getLogger(classOf[Object])
    // 输出字符串
    logger.debug("this is a debug msg")
    // 占位符
    // logger.debug("hi，welcome {}，today is {}", "admin", "Sunday")
    println((1L).toBinaryString)
    println((-1L).toBinaryString)
    println((-1L).toBinaryString)
    println((-1L).toBinaryString)

    println((1L << 10).toBinaryString)
    println((-1L).toBinaryString)
    println((~1L).toBinaryString)
    println((-1L << 10).toBinaryString)
    println((~(-1L << 10)).toBinaryString)
    println(~(-1L << 10))

    val m = -128.toByte;
    val q = 1.toByte;
    val p = (m - q) toByte; //这一步其实编译器会报错，其实是发现越界了，我们强行转化为byte就可以看出结果。
    System.out.println(p); // p的结果为：127
}

class Slf4jTest {
    synchronized {
        val a = 1;
    }

    def aa(): Unit ={
        synchronized {
            val a = 1
        }
    }
}
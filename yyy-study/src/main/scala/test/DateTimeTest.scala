package test

import java.text.SimpleDateFormat
import java.util.{Calendar, Date}
// https://blog.csdn.net/zjf280441589/article/details/50447533
object DateTimeTest extends App {

    // 获取当前时间

    println(new Date().getTime)
    println(System.currentTimeMillis)
    // Calendar
    val isSingleton = Calendar.getInstance() == Calendar.getInstance()
    println(s"Calendar.getInstance是否为单例：$isSingleton")


    val calendar = Calendar.getInstance()
    println(calendar.get(Calendar.DATE))
    Thread.sleep(100)
    calendar.setTime(new Date())
    println(calendar.get(Calendar.DATE))
    calendar.add(Calendar.DATE, 1)
    println(calendar.get(Calendar.DATE))
    // SimpleDateFormat
    val sdf = new SimpleDateFormat("yy年MM月dd日 hh时mm分ss秒") // 格式化的文档 https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html
    val timeString = sdf.format(System.currentTimeMillis())
    println(timeString)
    println(sdf.parse(timeString))


    //线程安全 https://my.oschina.net/leejun2005/blog/152253

    val formatterMap = new ThreadLocal[SimpleDateFormat]() {
        override protected def initialValue():SimpleDateFormat = {
            new SimpleDateFormat("yy++MM月dd日 hh时mm分ss秒")
        }
    }
    println(formatterMap.get().format(System.currentTimeMillis()))
}

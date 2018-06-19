package test.annotation

import java.lang.reflect.{InvocationHandler, Method, Proxy}
import javax.inject.Singleton

@Singleton
object TestAnnotation extends App {

  trait YYYService {
    @YYYAnnotation(name="yyy")
    def sayHello()

    def sayHelloWord()
  }

  class YYYServiceImpl extends YYYService {
    override def sayHello(): Unit = println("hello yyy. with YYYAnnotation")

    override def sayHelloWord(): Unit = println("hello yyy. without YYYAnnotation")
  }

  class YYYHandler(o: Object) extends InvocationHandler {
    override def invoke(proxy: Object, method: Method, args: Array[Object]): Object = {
      if (method.isAnnotationPresent(classOf[YYYAnnotation])) {
        println(method.getAnnotation[YYYAnnotation](classOf[YYYAnnotation]).name())
        println("start annotation")
        val ref = method.invoke(o, args: _*)
        println("end annotation")
        ref
      } else {
        method.invoke(o, args: _*)
      }
    }
  }

  val o = new YYYServiceImpl
  val handler = new YYYHandler(o)
  val service = Proxy.newProxyInstance(o.getClass.getClassLoader, o.getClass.getInterfaces, handler).asInstanceOf[YYYService]
  service.sayHello()
  service.sayHelloWord()
}

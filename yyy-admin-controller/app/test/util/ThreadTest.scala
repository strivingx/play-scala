package test.util

import java.util.concurrent.atomic.AtomicInteger

object ThreadTest extends App {
    val integer = new AtomicInteger()
    @volatile var  i = 0 // 读安全，写不安全

    private var a = 0
    private var flag = false
    val atomicInteger = new AtomicInteger(0)
    atomicIntegerTest()
    Thread.sleep(3000)
    System.out.println("最终结果是" + atomicInteger.get)
    System.out.println("最终结果是" + i)

    for (_ <- (1 to 1000000)) {
        val threadA = new ThreadA
        val threadB = new ThreadB
        threadA.start()
        threadB.start()
        threadA.join()
        threadB.join()
        a = 0
        flag = false
    }

    class ThreadA extends Thread {
        override def run(): Unit = {
            a = 1
            flag = true
        }
    }

    class ThreadB extends Thread {
        override def run(): Unit = {
            if (flag) if (a == 0) System.out.println("a == 0!!")
        }
    }

    import java.util.concurrent.Executors
    import java.util.concurrent.atomic.AtomicInteger



    private def atomicIntegerTest(): Unit = {
        val executorService = Executors.newFixedThreadPool(10000)
        for (_ <- (1 to 10000)) {
            executorService.execute(new Runnable {
                override def run(): Unit = {
                    for (_ <- (1 to 4)) {
                        System.out.println(atomicInteger.getAndIncrement)
                        i = i+1
                    }
                }
            })
        }
        executorService.shutdown()
    }



}

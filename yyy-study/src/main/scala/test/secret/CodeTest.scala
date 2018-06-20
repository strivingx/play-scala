package test.secret

import java.net.{URLDecoder, URLEncoder}

object CodeTest extends App {
    val charset = "utf-8"
    val str = "hello 你好"
    val encode = URLEncoder.encode(str, charset)
    println(encode)
    val decode = URLDecoder.decode(encode, charset)
    println(decode)
    println(URLDecoder.decode(encode, "GBK")) // 乱码

    val bytes = str.getBytes(charset)
    println(bytes)
    println(new String(bytes))

    println(new String(str.getBytes("utf-8")))
    println(new String(str.getBytes("gbk"))) // 乱码
}

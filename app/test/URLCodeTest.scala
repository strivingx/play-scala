package test

import java.net.{URLDecoder, URLEncoder}

object URLCodeTest extends App {

    val content = "中文编码"

    val encode = URLEncoder.encode("测试", "UTF-8")
    println(s"使用UTF-8编码，原始内容：$content, encode后内容$encode") // %E6%B5%8B%E8%AF%95
    println(s"GBK decode: ${URLDecoder.decode(encode, "GBK")}") // 娴嬭瘯
    println(s"UTF-8 decode: ${URLDecoder.decode(encode, "UTF-8")}") // 测试

    println(URLEncoder.encode("测试")) // 默认UTF-8
}

package test.util

import java.math.BigInteger
import java.security.MessageDigest

import org.apache.commons.codec.digest.DigestUtils

object MD5Test extends App {

    def MD5(content: String) = {
        val hexDigits = Array('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F')
        // 获得MD5摘要算法的 MessageDigest 对象
        val mdInst = MessageDigest.getInstance("MD5")
        // 使用指定的字节更新摘要
        mdInst.update(content.getBytes)
        // 获得密文
        val digestBytes = mdInst.digest
        // 把密文转换成十六进制的字符串形式
        val hex = digestBytes.flatMap(byte => Array(hexDigits(byte >>> 4 & 0xf), hexDigits(byte & 0xf)))
        new String(hex)
    }

    def getMD5(str: String): String = {
        // 生成一个MD5加密计算摘要
        val md = MessageDigest.getInstance("MD5")
        // 计算md5函数
        md.update(str.getBytes())
        // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
        // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
        new BigInteger(1, md.digest()).toString(16)
    }

    val str = "yyy"
    val md = MessageDigest.getInstance("MD5")
    md.update(str.getBytes)

    val bytes = md.digest()
    println(bytes)
    println(bytes.length)
    val res = new BigInteger(1, bytes).toString(16)
    println(res)
    bytes.foreach(println)
    println(DigestUtils.md5Hex(str).length)
    println(DigestUtils.md5Hex(str))
    println(MD5(str))
    println(getMD5(str))

}



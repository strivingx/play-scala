package test.secret

import java.security.NoSuchAlgorithmException
import javax.crypto.spec.SecretKeySpec
import javax.crypto.{KeyGenerator, Mac}
import javax.xml.bind.annotation.adapters.HexBinaryAdapter

/**
  * MAC算法工具类
  * 对于HmacMD5、HmacSHA1、HmacSHA256、HmacSHA384、HmacSHA512应用的步骤都是一模一样的。具体看下面的代码
  */
object MACCoder {

    @throws[NoSuchAlgorithmException]
    def initKey(algorithm: String): Array[Byte] = {
        // 初始化HmacMD5摘要算法的密钥产生器
        val generator = KeyGenerator.getInstance(algorithm)
        // 产生密钥
        val secretKey = generator.generateKey
        // 获得密钥
        secretKey.getEncoded
    }

    /**
      * HmacMd5摘要算法
      * 对于给定生成的不同密钥，得到的摘要消息会不同，所以在实际应用中，要保存我们的密钥
      */
    @throws[Exception]
    def encodeHmac(data: String, algorithm: String): String = encodeHmac(data.getBytes, algorithm)

    /**
      * HmacMd5摘要算法
      * 对于给定生成的不同密钥，得到的摘要消息会不同，所以在实际应用中，要保存我们的密钥
      */
    @throws[Exception]
    def encodeHmac(data: Array[Byte], algorithm: String): String = { // 还原密钥
        val key = initKey(algorithm)
        val secretKey = new SecretKeySpec(key, algorithm)
        // 实例化Mac
        val mac = Mac.getInstance(secretKey.getAlgorithm)
        //初始化mac
        mac.init(secretKey)
        //执行消息摘要
        val digest = mac.doFinal(data)
        new HexBinaryAdapter().marshal(digest) //转为十六进制的字符串
    }

    /**
      * HmacMd5摘要算法
      */
    def encodeHmacMD5(data: Array[Byte]): String = encodeHmac(data, "HmacMD5")

    /**
      * HmacSHA1摘要算法
      */
    def encodeHmacSHA(data: Array[Byte]): String = encodeHmac(data, "HmacSHA1")

    def encodeHmacSHA256(data: Array[Byte]): String = encodeHmac(data, "HmacSHA256")

    def encodeHmacSHA384(data: Array[Byte]): String = encodeHmac(data, "HmacSHA384")

    def encodeHmacSHA512(data: Array[Byte]): String = encodeHmac(data, "HmacSHA512")
}

object MACTest {
    @throws[Exception]
    def main(args: Array[String]): Unit = {
        val testString = "asdasd"
        System.out.println(MACCoder.encodeHmacMD5(testString.getBytes))
        System.out.println(MACCoder.encodeHmacSHA(testString.getBytes))
        System.out.println(MACCoder.encodeHmacSHA256(testString.getBytes))
        System.out.println(MACCoder.encodeHmacSHA384(testString.getBytes))
        System.out.println(MACCoder.encodeHmacSHA512(testString.getBytes))
    }
}


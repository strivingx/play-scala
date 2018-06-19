package test

object RegexTest extends App {
    // 中文
    //a{n,m} a出现n-m次，n默认为0，m默认为无穷 a+ : a{1,}  a* : a{0,} a? : a{0,1}
    // ^开头   $结尾
    require("fsdafasdfaewfsvfgeragsdfss".matches("[a-z]*")) //字母
    require("123432123".matches("[\\d]+")) // 数字

    require("aaabbbb".matches("a+b+")) //
    require("中文测试".matches("[\\u4e00-\\u9fa5]+"))

}

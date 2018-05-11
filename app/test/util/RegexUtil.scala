package test.util

object RegexUtil extends App {

    val REGEX_EMAIL = "^[a-z0-9]+([._\\\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$"
    val REGEX_ID_CARD = "(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)"
    val REGEX_TELEPHONE = "^[1][3,4,5,7,8][\\d]{9}$"

    require("fafasd@qq.com".matches(REGEX_EMAIL))
    require("41270100000000000X".matches(REGEX_ID_CARD))
    require("13000000000".matches(REGEX_TELEPHONE))
}

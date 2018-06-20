package dao


object TestSpec extends App {

  case class AA(code: Long, msg: String)

  val str = AA(11, "aaa")
  str match {
    case de: AA =>
      de.code match {
        case 1001 => throw new IllegalArgumentException(s"不存在这个词表Id")
        case 1002 => throw new IllegalArgumentException(s"该用户没有操作这个词表的权限")
        case 1003 => throw new IllegalArgumentException(s"词表里不存在这个词条")
        case 1004 => throw new IllegalArgumentException(s"同义词出现冲突")
        case 1005 => throw new IllegalArgumentException(s"词表里已经存在这个词条")
        case _ => println("default")
      }
    case a => println(a.msg)
  }
}
package test

class MainObject private (val classVal:String) {
  private val privateVal = "private"
  val defaultVal = "default"
  def this()={
    this("classVal")
  }
}

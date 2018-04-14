package test.scala

object OptionTest extends App {
  val bigDataSkills =
    Map("Java" -> "first",
      "Hadoop" -> "second",
      "Spark" -> "third",
      "storm" -> "forth",
      "hbase" -> "fifth",
      "hive" -> "sixth",
      "photoshop" -> null)

  println(bigDataSkills.get("Java") == Some("first"))
  println(bigDataSkills.get("photoshop").get == null)
  println(bigDataSkills.get("Spark").get == "third")
  println(bigDataSkills.get("abc123") == None)

  private def get(key:String): String = {
    bigDataSkills.get(key) match {
      case Some(value) => value
      case None => "no value"
    }
  }

  private def get(map: Map[String, String], key: String): String = {
    map.get(key) match {
      case Some(value) => value
      case None => "no value"
    }
  }

  println(get(bigDataSkills, "Java") == "first")
  println(get( "Spark") == Some("Spark"))
  println(bigDataSkills.get("Spark").get == "third")
  println(get("abc123") ==  "no value")
}
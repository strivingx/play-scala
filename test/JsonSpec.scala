import ai.x.play.json.Jsonx
import org.scalatest.FlatSpec
import play.api.libs.json.Json

class AIFlatSpec extends FlatSpec {

  "scala test" should "be ok" in {
    case class ReleaseTestResult(totalResult: String)
    implicit val ReleaseTestResultFormats = Jsonx.formatCaseClassUseDefaults[ReleaseTestResult]
    val str =
      """{
    "298423366455394304": {
        "function": {
            "date": "2018-05-17 10:42:38",
            "result": "Pass",
            "pass_num": "1",
            "fail_num": "0"
        },
        "performance": {
            "date": "2018-05-11",
            "job": "383",
            "result": "Pass"
        },
        "totalResult": "Fail",
        "availability": {
            "date": "2018-05-16",
            "count": "98",
            "result": "Fail",
            "value": "59.6939"
        }
    }
}"""
    println(Json.parse(str).as[Map[String, ReleaseTestResult]])
  }

}
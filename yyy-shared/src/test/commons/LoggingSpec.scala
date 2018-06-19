package commons

import org.scalatest.FlatSpec

class LoggingSpec extends FlatSpec with Logging {
    "logging" should "run ok" in {
        log.error("test")
    }
}

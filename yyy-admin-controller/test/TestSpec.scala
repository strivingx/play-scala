import org.scalatestplus.play._

/**
 * Add your integration spec here.
 * An integration test will fire up a whole play application in a real (or headless) browser.
 */
class TestSpec extends PlaySpec with OneServerPerTest with OneBrowserPerTest with HtmlUnitFactory {

  "Application" should {

    "work from within a browser" in {

      println(1521117411000L - System.currentTimeMillis())
    }
  }
}

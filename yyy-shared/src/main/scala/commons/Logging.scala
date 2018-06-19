package commons

import play.api.Logger

trait Logging {
    val log = Logger(getClass)
}

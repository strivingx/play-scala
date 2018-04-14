package test

import java.util.UUID

object UUIDTest extends App {

   println(UUID.randomUUID().toString.replace("-", "").toLowerCase.length)
}



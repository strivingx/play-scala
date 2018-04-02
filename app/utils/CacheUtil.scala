package utils

import java.util.concurrent.Callable

import com.google.common.cache.CacheBuilder
import commons.Logging

object CacheUtil extends Logging{
  private val usernameToIds = CacheBuilder.newBuilder().maximumSize(1000).build[String, java.lang.Long]()

  private def getUserIdByUsername(username: String): java.lang.Long = {
    usernameToIds.get(username, new Callable[java.lang.Long] {
      override def call(): java.lang.Long = {
        val userId = 1L
        log.debug(s"getUserIdByUsername by cache. username: $username, userId: $userId")
        userId
      }
    })
  }

}

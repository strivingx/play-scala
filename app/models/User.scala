package models

case class User(userId: String = "", username: String, password: String, mail: Option[String] = None, gender: Option[Boolean] = None, age: Option[Int] = None)

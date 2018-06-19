package models

package object cases {

  case class UserRequest(pageNum: Int = 1, pageSize: Int = 10, is_list: Boolean = true)

  case class User(username: String, password: String, mail: Option[String] = None, gender: Option[Boolean] = None, age: Option[Int] = None)

  case class UserSimpleInfo(username: String, mail: Option[String] = None, gender: Option[Boolean] = None, age: Option[Int] = None)


  case class BookRequest(pageNum: Int = 1, pageSize: Int = 10, is_list: Boolean = true)

  case class Book(bookId: String, name: String)

  case class BookListInfo(bookId: String, name: String)

}

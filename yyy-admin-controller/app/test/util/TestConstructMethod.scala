package test.util

object TestConstructMethod extends App {

    class User(usernane: String, password: String) {
        def this(usernane: String) {
            this(usernane, "")
        }

        override def toString: String = s"usernane: $usernane, password: $password"
    }

    class Student(usernane: String, password: String, studentNo: String) extends User(usernane) {
        self: User =>

        println(this == self)
        println(self.studentNo)
        println(self.getClass)
        def this(usernane: String) {
            this(usernane, "", "")
        }

        override def toString: String = s"usernane: $usernane, password: $password, studentNo: $studentNo"
    }

    val user = new User("aaa", "bbb")

    println(new User("aaa", "bbb"))
    println(new User("ccc"))
    println(new Student("ccc"))
    println(new Student("ddd", "eee", "fff"))

}

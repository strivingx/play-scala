package funs

import scala.collection.mutable

import org.scalatestplus.play._
class StackPlaySpec extends PlaySpec {

  "A Stack" must {
    "pop values in last-in-first-out order" in {
      val stack = new mutable.Stack[Int]
      stack.push(1)
      stack.push(2)
      stack.pop() mustBe 2
      stack.pop() mustBe 1
    }
    "throw NoSuchElementException if an empty stack is popped" in {
      val emptyStack = new mutable.Stack[Int]

      assertThrows[NoSuchElementException] {
        emptyStack.pop()
      }

      a[NoSuchElementException] must be thrownBy {
        emptyStack.pop()
      }
    }
  }

  "matchers" must {
    "be ok" in {
      "Hello world" must endWith("world")
    }
  }

  /*"MyService#isDailyData" should {
    "return true if the data is from today" in {
      val mockDataService = mock[DataService]
      when(mockDataService.findData) thenReturn Data(new java.util.Date())

      val myService = new MyService() {
        override def dataService = mockDataService
      }

      val actual = myService.isDailyData
      actual mustBe true
    }
  }
*/
  "mockito data" must {
    "be ok" in {
      // "Hello world" must endWith("world")
    }
  }

}
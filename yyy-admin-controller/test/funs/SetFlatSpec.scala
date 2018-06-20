package funs

import org.scalatest.tagobjects.Slow
import org.scalatest.{FlatSpec, Matchers, Tag}

import scala.collection.mutable

// scala> (new StackSpec).execute()


// http://www.scalatest.org/user_guide
class SetFlatSpec extends FlatSpec with Matchers {

  object TagTest extends Tag("com.yyy.TagTest")

  "An empty Set" should "have size 0" in {
    assert(Set.empty.size == 0)
  }
  // Each test in a FlatSpec is composed of a sentence that specifies a bit of required behavior and a block of code that tests it.
  // The sentence needs a subject, such as "A Stack"; a verb, either should, must, or can; and the rest of the sentence.
  // 主题 should 语句（required behavior） in { }
  // it代表上一个主题，此处代表An empty Set
  it should "produce NoSuchElementException when head is invoked" in {
    assertThrows[NoSuchElementException] {
      Set.empty.head
    }
  }

  "A Set" should "have size 2" in {
    assertResult(2) {
      Set(1,2).size
    }
  }

  "A Stack" should "pop values in last-in-first-out order" in {
    val stack = new mutable.Stack[Int]
    stack.push(1)
    stack.push(2)
    assert(stack.pop() === 2)
    assert(stack.pop() === 1)
  }

  it should "throw NoSuchElementException if an empty stack is popped" in {
    val emptyStack = new mutable.Stack[String]
    assertThrows[NoSuchElementException] {
      emptyStack.pop()
    }
  }


  it should "throw IndexOutOfBoundsException" in {
    assertThrows[IndexOutOfBoundsException] {
      "hi".charAt(-1)
    }

    val caught = intercept[IndexOutOfBoundsException] {
      "hi".charAt(-1)
    }
    assert(caught.getMessage.indexOf("-1") != -1)
    println(caught)
  }

  "The Scala language" must "add correctly" taggedAs(Slow) in {
    val sum = 1 + 1
    assert(sum === 2)
  }

  it must "subtract correctly" taggedAs(Slow, TagTest) in {
    val diff = 4 - 1
    assert(diff === 3)
  }

  // ignore会被忽略
  ignore should "be ok" in{
    fail("I've got a bad feeling about this")
    cancel("Can't run the test because no internet connection was found")
    assume(false, "The database was down again")
  }

  // ignore会被忽略
  "ignore" should "be ok" ignore{
    fail("I've got a bad feeling about this")
    cancel("Can't run the test because no internet connection was found")
    assume(false, "The database was down again")
  }

}
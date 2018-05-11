package funs

import java.util.NoSuchElementException

import com.google.inject.ImplementedBy
import org.junit.Assert
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}

// scala> (new StackSpec).execute()
import org.mockito.Mockito._
// 官网： http://www.scalatest.org/user_guide
// 单元测试小结： https://segmentfault.com/a/1190000006731125
// mockito实例： https://segmentfault.com/a/1190000006746409
class MockitoFlatSpec extends FlatSpec with Matchers with MockitoSugar {

  case class User(username: String, password: String)

  @ImplementedBy(classOf[UserServiceImpl])
  trait UserService {
    def getUserByUsername(name: String): User

    def sayHello() = {
      "hello world"
    }
  }

  class UserServiceImpl extends UserService {
    override def getUserByUsername(name: String): User = new User("", "")
  }

  object UserServiceImpl extends UserServiceImpl

  "mockito mock" should "run ok" in {
    val userService = mock[UserService]

    when(userService.getUserByUsername("yyy")).thenReturn(new User("yyy", "123")).thenReturn(new User("zzz", "456"))
    assert(userService.getUserByUsername("yyy").password == "123")
    assert(userService.getUserByUsername("yyy").password == "456")
    assert(userService.getUserByUsername("yyy").password == "456")
    assert(userService.sayHello() == null)

    val list = mock[java.util.List[Int]]

    when(list.toString()).thenReturn("this is a mock list")
    assert(list.toString == "this is a mock list")

    when(list.add(100)).thenThrow(new RuntimeException("can't insert 100")) // TODO 为什么不能抛java.lang.Exception
    assertThrows[RuntimeException] {
      list.add(100)
    }

    doThrow(new NoSuchElementException()).when(list).get(100)
    assertThrows[NoSuchElementException] {
      list.get(100)
    }

  }

  // Mockito 提供的 spy 方法可以包装一个真实的 Java 对象, 并返回一个包装后的新对象. 若没有特别配置的话, 对这个新对象的所有方法调用, 都会委派给实际的 Java 对象.
  // spy将真实调用和mock进行了结合
  "mockito spy" should "run ok" in {
    val list = mock[java.util.List[Int]] // 任何方法都会返回默认值，比如null, 0
    val spyList = spy(new java.util.ArrayList[Int]()) // 任何方法都是调用实际方法

    assert(list.get(0) == 0)
    assertThrows[IndexOutOfBoundsException] {
      spyList.get(0)
    }

    doReturn(1, Nil).when(spyList).get(0) // 此处做了假设，当spyList执行get(0)时会返回1
    assert(spyList.get(0) == 1)
    assertThrows[IndexOutOfBoundsException] {
      when(spyList.get(1)).thenReturn(2) // spyList.get(1)已经触发了真正的查询会抛出异常
      assert(spyList.get(1) == 2)
    }

    val userServiceSpy = spy(new UserServiceImpl) // 任何方法都是调用实际方法

    assert(userServiceSpy.sayHello() == "hello world")
  }

  // Mockito 会追踪 Mock 对象的所用方法调用和调用方法时所传递的参数. 我们可以通过 verify() 静态方法来来校验指定的方法调用是否满足断言.
  "mockito verify" should "run ok" in {
    val list = mock[java.util.List[Int]] // 任何方法都会返回默认值，比如null, 0
    list.add(1)
    list.add(2)
    list.add(3)
    list.add(3)
    list.add(3)
    // list.isEmpty
    verify(list, atLeastOnce).add(1) // list至少调用过1次add(1)
    verify(list, times(1)).add(2)
    verify(list, times(3)).add(3)// list调用过3次add(1)
    verify(list, never).isEmpty// list从没调用过isEmpty方法
  }

  "mockito capture" should "run ok" in {
    import org.mockito.ArgumentCaptor
    import java.util
    val list = util.Arrays.asList(1, 2)
    val mockedList = mock[util.List[Int]]
    val argument: ArgumentCaptor[util.List[Int]] = ArgumentCaptor.forClass(classOf[util.List[Int]])
    mockedList.addAll(list)
    println(argument.getAllValues)
    // 添加完数据之后再验证
    verify(mockedList, atLeastOnce()).addAll(argument.capture)
    assert(argument.getAllValues.size() == 1)

    mockedList.addAll(list)
    println(argument.getAllValues)

    // list中有两个元素，verify(...).addAll之后argument.getAllValues.size = 3
    verify(mockedList, atLeastOnce()).addAll(argument.capture) //
    assert(argument.getAllValues.size() == 3)

    // list中有两个元素，verify(...).addAll之后argument.getAllValues.size = 5
    verify(mockedList, atLeastOnce()).addAll(argument.capture)
    assert(argument.getAllValues.size() == 5)

    // mock的一切方法在不处理时都返回默认值
    assert(mockedList.size() == 0)

    Assert.assertEquals(2, argument.getValue.size)
    Assert.assertEquals(list, argument.getValue)
  }

}
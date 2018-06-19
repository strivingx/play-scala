package test

abstract class Node[T <: Ordered[T]] {
}



//T <: Ordered[T]  T是 Ordered[T]的子类
//T >: Ordered[T]  T是 Ordered[T]的超类

//T <% Ordered[T]  T可以被隐式转换为Ordered[T]   视图限定
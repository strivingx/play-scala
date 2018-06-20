package test.rmi

import java.rmi.server.UnicastRemoteObject
import java.rmi.{Remote, RemoteException}

trait RMIService extends Remote{
  @throws(classOf[RemoteException])
  def sayHello()
}
class RMIServiceImpl extends UnicastRemoteObject with RMIService {
  def sayHello() = println("hello yyy")
}

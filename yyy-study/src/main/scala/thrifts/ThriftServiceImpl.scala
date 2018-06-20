package thrifts

class ThriftServiceImpl extends ThriftService.Iface {
  override def add(a: Int, b: Int) = a + b
}
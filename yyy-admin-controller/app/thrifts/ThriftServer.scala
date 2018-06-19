package thrifts

import org.apache.thrift.protocol.TBinaryProtocol
import org.apache.thrift.server.{TServer, TSimpleServer}
import org.apache.thrift.transport.TServerSocket

object ThriftServer extends App {
	val serverTransport = new TServerSocket(7911)
	val proFactory = new TBinaryProtocol.Factory()
	val processor = new ThriftService.Processor[ThriftService.Iface](new ThriftServiceImpl())
	val tArgs = new TServer.Args(serverTransport)
	tArgs.processor(processor)
	tArgs.protocolFactory(proFactory)

	val server = new TSimpleServer(tArgs)
	System.out.println("Start server on port 7911....")
	server.serve()
}
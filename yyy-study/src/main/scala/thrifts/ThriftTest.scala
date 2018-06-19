package thrifts

import org.apache.thrift.protocol.TBinaryProtocol
import org.apache.thrift.transport.TSocket

object ThriftTest extends App {
			val transport = new TSocket("localhost", 7911);
			transport.open();
			val protocol = new TBinaryProtocol(transport);
			val client = new ThriftService.Client(protocol);
			println(client.add(77, 5));
			transport.close();
}


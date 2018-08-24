package test.slf4j;

class Node {
    int value;
    Node next;
}
public class TestSynchronized {
    Object instance = new Object();

    public void test() {
        int i = 0;
        synchronized (instance) {
            for (int j = 0; j < 1000000; j++) {
                i++;
            }
        }
    }

}

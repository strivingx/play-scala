package test;

/**
 *-Xmx20m -Xms20m -Xmn14m -XX:+UseParNewGC  -XX:+UseConcMarkSweepGC
 *-XX:+UseCMSInitiatingOccupancyOnly  -XX:CMSInitiatingOccupancyFraction=75
 *-XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintHeapAtGC
 */
public class JVM {

    private static final int _1MB = 1024 * 1024;
    private static final int _1K = 1024;

    public static void main(String[] args) throws Exception {
        byte[][] arr = new byte[10000][];
        for (int i = 0; i< 1200; i++) {
            arr[i] = new byte[10* _1K];
        }
        System.in.read();
    }
}


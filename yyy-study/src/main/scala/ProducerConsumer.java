import java.util.LinkedList;
import java.util.Random;

class ProducerConsumer {
    public static void main(String args[]) {
        LinkedList<Integer> queue = new LinkedList<>();
        int maxSize = 10;
        Random random = new Random();
        //生产者
        Thread producer = new Thread(new Runnable() {

            @Override
            public void run() {
                while (true) {
                    synchronized (queue) {
                        while (queue.size() == maxSize) {
                            try {
                                queue.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        int i = random.nextInt();
                        queue.offer(i);
                        System.out.println("Producing value : " + i + "size: " + queue.size());
                        queue.notify();
                    }

                    try {
                        Thread.sleep(random.nextInt(300));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        Thread consumer = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {int value = 0;
                    synchronized (queue) {
                        while (queue.isEmpty()) {
                            try {
                                queue.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        value = queue.poll();
                        queue.notify();
                    }

                    try {
                        Thread.sleep(random.nextInt(300));
                        System.out.println("take from queue:" + value + "size: " + queue.size());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        producer.start();
        consumer.start();

    }
}

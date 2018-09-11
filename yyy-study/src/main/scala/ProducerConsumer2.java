import java.util.LinkedList;
import java.util.Random;

class ProducerConsumer2 {
    public static void main(String args[]) throws InterruptedException {
        LinkedList<Integer> queue = new LinkedList<>();
        int maxSize = 10;
        Random random = new Random();
        Object consumerObj = new Object();
        Object producerObj = new Object();
        //生产者
        Thread producer = new Thread(new Runnable() {

            @Override
            public void run() {
                while (true) {
                    synchronized (queue) {
                        while (queue.size() == maxSize) {
                            try {
                                producerObj.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        int i = random.nextInt();
                        queue.offer(i);
                        System.out.println("Producing value : " + i + "size: " + queue.size());
                        consumerObj.notify();
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
                                consumerObj.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        value = queue.poll();
                        producerObj.notify();
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

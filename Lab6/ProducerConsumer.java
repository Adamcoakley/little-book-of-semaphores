package Lab6;

import java.util.concurrent.Semaphore;

public class ProducerConsumer {

    public static void main(String[] args) {

        Semaphore empty = new Semaphore(1);
        Semaphore full = new Semaphore( 0);
        Semaphore mutex = new Semaphore(1);

        Producer producer = new Producer(empty, full, mutex);
        Consumer consumer = new Consumer(empty, full, mutex);

        Thread producerThread = new Thread(producer);
        Thread consumerThread = new Thread(consumer);

        producerThread.start();
        consumerThread.start();
    }

    static class Producer implements Runnable {

        Semaphore empty, full, mutex;

        public Producer(Semaphore empty, Semaphore full, Semaphore mutex){
            this.empty = empty;
            this.full = full;
            this.mutex = mutex;
        }

        @Override
        public void run() {
            int index = 0;
            while(index < 5){
                try{
                    empty.acquire();
                    mutex.acquire();
                    Thread.sleep(200);
                    System.out.println("Producer produced item.");
                    mutex.release();
                    full.release();
                    index--;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class Consumer implements Runnable {

        Semaphore empty, full, mutex;

        public Consumer(Semaphore empty, Semaphore full, Semaphore mutex){
            this.empty = empty;
            this.full = full;
            this.mutex = mutex;
        }

        @Override
        public void run() {
            int index = 5;
            while(index > 0){
                try{
                    full.acquire();
                    mutex.acquire();
                    Thread.sleep(200);
                    System.out.println("Consumer consumed item.");
                    mutex.release();
                    empty.release();
                    index--;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

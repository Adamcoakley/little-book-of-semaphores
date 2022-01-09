package Lab6;

import java.util.concurrent.Semaphore;

public class ProducerConsumer{
    public static void main(String[] args) {

        Semaphore producerSem = new Semaphore(1);
        Semaphore consumerSem = new Semaphore(0);

        Producer producer = new Producer(producerSem, consumerSem);
        Consumer consumer = new Consumer(consumerSem, producerSem);

        Thread producerThread = new Thread(producer, "ProducerThread");
        Thread consumerThread = new Thread(consumer, "ConsumerThread");

        producerThread.start();
        consumerThread.start();
    }
}

class Producer implements Runnable{

    Semaphore producerSem, consumerSem;

    public Producer(Semaphore producerSem,Semaphore consumerSem) {
        this.producerSem = producerSem;
        this.consumerSem = consumerSem;
    }

    public void run() {
        for(int index=0; index<5; index++){
            try {
                producerSem.acquire();
                System.out.println("Producer produced item: " +index);
                consumerSem.release();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Consumer implements Runnable{

    Semaphore producerSem, consumerSem;

    public Consumer(Semaphore consumerSem,Semaphore producerSem) {
        this.consumerSem=consumerSem;
        this.producerSem=producerSem;
    }

    public void run() {
        for(int index=0; index<5; index++){
            try {
                consumerSem.acquire();
                System.out.println("Consumer consumed item: "+index);
                producerSem.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}


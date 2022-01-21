package Lab6;

/**
 *
 * @author  Adam Coakley
 * @since   2022-01-07
 *
 */

import java.util.concurrent.Semaphore;

public class ProducerConsumer{
    public static void main(String[] args) {

        /**
         * Create a semaphore for both producer and consumer
         * consumerSem initialised to 0 to ensure producerSem executes first
         */
        Semaphore producerSem = new Semaphore(1);
        Semaphore consumerSem = new Semaphore(0);

        /**
         * Create a producer and consumer object
         */
        Producer producer = new Producer(producerSem, consumerSem);
        Consumer consumer = new Consumer(consumerSem, producerSem);

        /**
         * Create a producer and consumer thread
         */
        Thread producerThread = new Thread(producer, "ProducerThread");
        Thread consumerThread = new Thread(consumer, "ConsumerThread");

        /**
         * Start the producer and consumer threads
         */
        producerThread.start();
        consumerThread.start();
    }
}

class Producer implements Runnable{

    Semaphore producerSem, consumerSem;

    /**
     * Producer constructor
     * @param producerSem - producer semaphore
     * @param consumerSem - consumer semaphore
     */
    public Producer(Semaphore producerSem,Semaphore consumerSem) {
        this.producerSem = producerSem;
        this.consumerSem = consumerSem;
    }

    /**
     * before the producer can produce an item it must acquire a permit from producer semaphore
     * prints out a message that the item has been produced + the index of that item
     * after producing an item, it releases the consumer semaphore to notify the consumer
     */
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

    /**
     * Consumer constructor
     * @param consumerSem - consumer semaphore
     * @param producerSem - producer semaphore
     */
    public Consumer(Semaphore consumerSem,Semaphore producerSem) {
        this.consumerSem=consumerSem;
        this.producerSem=producerSem;
    }

    /**
     * before the consumer can consume an item it must acquire a permit from consumer semaphore
     * prints out a message that the item has been consumed + the index of that item
     * after consuming an item, it releases the producer semaphore to notify the producer
     */
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


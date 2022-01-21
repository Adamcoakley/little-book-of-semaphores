package Lab3;

import java.util.concurrent.Semaphore;

/**
 *
 * Implementation of a reusable barrier using semaphores
 *
 * @author  Adam Coakley
 * @since   2021-10-11
 *
 */
public class ReusableBarrier {

    class Barrier extends Thread {

        Semaphore turnstile1;
        Semaphore turnstile2;
        Semaphore mutex;
        int[] count;
        int numOfThreads;
        int numOfPhases;

        /**
         * Constructor for a Barrier object
         * @param turnstile1 - turnstile for threads to stop upon arrival at phase one
         * @param turnstile2 - turnstile for threads to stop upon arrival at phase two
         * @param mutex - lock used to protect the critical section
         * @param count - Number of threads that arrived to the barrier
         * @param numOfThreads - Number of threads in total
         * @param numOfPhases - Number of times the threads pass through the barrier
         */
        Barrier(Semaphore turnstile1, Semaphore turnstile2, Semaphore mutex, int[] count, int numOfThreads, int numOfPhases) {
            this.turnstile1 = turnstile1;
            this.turnstile2 = turnstile2;
            this.mutex = mutex;
            this.count = count;
            this.numOfThreads = numOfThreads;
            this.numOfPhases = numOfPhases;
        }

        public void run() {
            for (int i = 0; i < numOfPhases; i++) {
                try {
                    /**
                     * Beginning of phase one, acquire the mutex lock
                     * increment the count as threads arrive at the barrier
                     */
                    mutex.acquire();
                    count[0]++;
                    /**
                     * When all the threads arrive at the first turnstile,
                     * we lock the second turnstile and open the first
                     */
                    if (count[0] == numOfThreads) {
                        turnstile2.acquire();
                        turnstile1.release();
                    }
                    mutex.release();
                    turnstile1.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                turnstile1.release();
                /**
                 * Print out the names of the threads that have reached phase one and two
                 */
                System.out.println("Critical section of " + this.getName() + " at phase " + (i + 1));

                try {
                    /**
                     * Beginning of phase one, acquire the mutex lock
                     * decrement the count as threads leave phase one and head for phase two
                     */
                    mutex.acquire();
                    count[0]--;
                    /**
                     * When all threads arrive at turnstile 2,
                     * we lock the first turnstile and open the second
                     */
                    if (count[0] == 0) {
                        turnstile1.acquire();
                        turnstile2.release();
                    }
                    mutex.release();
                    turnstile2.acquire();
                    turnstile2.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        /**
         * Variables initialised:
         * reusableBarrier object
         * numOfThreads = number of threads
         * numOfLoops = number of times the reusable barrier is used
         * countArrived = counter to keep track of threads arriving at the barrier
         */
        ReusableBarrier reusableBarrier = new ReusableBarrier();
        int numOfThreads = 5;
        int numOfLoops = 2;
        int[] countArrived = {0};

        /**
         * Initially the first turnstile is locked and the second is open
         */
        Semaphore turnstile1 = new Semaphore(0);
        Semaphore turnstile2 = new Semaphore(1);
        /**
         * Mutex is a semaphore initialised to one
         */
        Semaphore mutex = new Semaphore(1);

        /**
         * Initialise barrier object
         * Start each thread
         */
        Barrier[] threads = new Barrier[numOfThreads];
        for (int i = 0; i < numOfThreads; i++) {
            Barrier barrier = reusableBarrier.new Barrier(turnstile1, turnstile2, mutex, countArrived, numOfThreads, numOfLoops);
            threads[i] = barrier;
            barrier.start();
        }
    }
}


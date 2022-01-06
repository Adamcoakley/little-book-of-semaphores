package Lab3;

import java.util.concurrent.Semaphore;

public class ReusableBarrier {

    class Barrier extends Thread {
        Semaphore turnstile1;
        Semaphore turnstile2;
        Semaphore mutex;
        int[] count;    // Number of threads that arrived to the barrier
        int numOfThreads;
        int numOfPhases; // Number of times the threads pass through the barrier

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
                    // Phase one
                    mutex.acquire();
                    count[0]++;
                    if (count[0] == numOfThreads) {     // All arrived at turnstile 1
                        turnstile2.acquire();           // Lock the second turnstile
                        turnstile1.release();           // Open the first turnstile
                    }
                    mutex.release();
                    //acquire(turnstile1);
                    turnstile1.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                turnstile1.release();
                System.out.println("Critical section of " + this.getName() + " at phase " + (i + 1));

                // Phase two
                try {
                    mutex.acquire();
                    count[0]--;
                    if (count[0] == 0) {              // All arrived at turnstile 2
                        turnstile1.acquire();         // Lock the first turnstile
                        turnstile2.release();         // Open the second turnstile
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
        ReusableBarrier reusableBarrier = new ReusableBarrier();
        int numOfThreads = 5;
        int numOfLoops = 2;
        int[] countArrived = {0};
        Semaphore turnstile1 = new Semaphore(0);
        Semaphore turnstile2 = new Semaphore(1);    // Second turnstile is open to begin with
        Semaphore mutex = new Semaphore(1);         // mutex is a semaphore set to 1
        Barrier[] threads = new Barrier[numOfThreads];
        for (int i = 0; i < numOfThreads; i++) {
            Barrier barrier = reusableBarrier.new Barrier(turnstile1, turnstile2, mutex, countArrived, numOfThreads, numOfLoops);
            threads[i] = barrier;
            barrier.start();
        }
        for (int i = 0; i < numOfThreads; i++) {
            try {
                threads[i].join();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}


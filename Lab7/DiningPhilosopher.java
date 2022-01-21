package Lab7;

/**
 *
 * @author  Adam Coakley
 * @since   2022-01-10
 *
 */

import java.util.Random;
import java.util.concurrent.Semaphore;

public class DiningPhilosopher {
    /**
     * Variables initialised:
     * finished - number of philosophers finished eating
     * NUM_PHILOSOPHERS = total number of philosophers
     * NUM_FORKS = total number of forks
     * philosophers = array of 5 philosophers
     * forks = array of 5 forks
     */
    private static int finished = 0;
    private static final int NUM_PHILOSOPHERS = 5;
    private static final int NUM_FORKS = 5;
    private static Philosopher[] philosophers = new Philosopher[NUM_PHILOSOPHERS];
    private static Fork[] forks = new Fork[NUM_FORKS];

    /**
     * function that updates the finished counter
     * once a philosopher has finished eating
     *
     * if the counter is equal to the number of philosophers
     * the system prints out that all philosophers have finished eating
     */
    static void updatedFinished() {
        finished++;
        if (finished == NUM_PHILOSOPHERS) {
            System.out.println("All 5 philosophers have finished eating!");
            System.exit(0);
        }
    }

    static class Fork {
        private final Semaphore mutex = new Semaphore(1);
        private final int id;

        /**
         * Fork constructor
         * @param id - a number used to identify each fork
         */
        Fork(int id) {
            this.id = id + 1;
        }

        /**
         * the philosopher acquires the mutex to simulate picking up a fork
         */
        void pickUp() {
            try {
                mutex.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        /**
         * the philosopher releases the mutex to simulate putting down a fork
         */
        void putDown() {
            mutex.release();
        }

        /**
         * function to return the number used to identify each fork
         * @return int
         */
        int getID() {
            return this.id;
        }
    }

    static class Philosopher extends Thread {
        /**
         * Variables initialised:
         * id - the number used to identify each fork
         * leftFork - a fork object that represents the left fork
         * rightFork - a fork object that represents the right fork
         */
        private final int id;
        private final Fork leftFork;
        private final Fork rightFork;

        /**
         * Philosopher constructor
         * @param id - a number used to identify each fork
         * @param leftFork - a fork object that represents the left fork
         * @param rightFork - a fork object that represents the right fork
         */
        Philosopher(int id, Fork leftFork, Fork rightFork) {
            this.id = id + 1;
            this.leftFork = leftFork;
            this.rightFork = rightFork;
        }

        /**
         * function that prints a message to showcase a philosopher is eating
         * invokes the updateFinished() method that updates the finished counter
         */
        void eat() {
            try {
                Thread.sleep(500);
                System.out.println("Philosopher " + id + " is eating");
                updatedFinished();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        /**
         * function invokes the pickUp() method and prints a message to showcase a philosopher has picked up a fork
         * @param fork - either right or left fork
         */
        void acquire(Fork fork) {
            fork.pickUp();
            System.out.println("Philosopher " + id + " has picked up fork " + fork.getID());
        }

        /**
         *
         */
        @Override
        public void run() {
            try {
                /**
                 * Put the threads to sleep for some time
                 */
                Thread.sleep(new Random().nextInt(500));
                /**
                 * Try to acquire the forks
                 */
                acquire(leftFork);
                acquire(rightFork);
                /**
                 * Once both forks have been acquired, a philosopher eats then releases the forks
                 * a message is printed showcasing which philosopher has released which fork
                 */
                eat();
                System.out.println("Philosopher " + id + " released fork " + leftFork.getID() + " and fork " + rightFork.getID());
                leftFork.putDown();
                rightFork.putDown();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        /**
         * initialise the forks
         */
        for (int i = 0; i < NUM_FORKS; i++) {
            forks[i] = new Fork(i);
        }
        /**
         * Initialise the philosophers and start each thread
         */
        for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
            philosophers[i] = new Philosopher(i, forks[i], forks[(i + 1) % NUM_FORKS]);
            philosophers[i].start();
        }
    }
}


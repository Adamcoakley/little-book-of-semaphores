package Lab5;
/**
 *
 * @author  Adam Coakley
 * @since   2022-01-05
 *
 */

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class Queue {
    /**
     * Variables initialised:
     * leaders = counter for number of leaders
     * followers = counter for number of followers
     * mutex = used to guarantee exclusive access to counters
     * leaderQueue = queue where leaders wait
     * followerQueue = queue where followers wait
     * executor = a thread pool
     */
    private int leaders = 0;
    private int followers = 0;
    private final Semaphore mutex = new Semaphore(1);
    private final Semaphore leaderQueue = new Semaphore(0);
    private final Semaphore followerQueue = new Semaphore(0);
    private ExecutorService executor = Executors.newCachedThreadPool();

    /**
     * Leader section:
     * When a leader arrives it acquires the mutex
     * If there is a follower waiting, the leader decrements the follower count and releases the followerQueue for another follower
     * The leader and follower proceed to dance and release the mutex lock for another thread
     *
     * If there is no follower waiting, the leader acquires the leaderQueue but gives up the mutex to wait on a follower
     */
    public void leaderFollower() {
        for (int index = 0; index < 5; index++) {
            executor.submit(() -> {
                try {
                    mutex.acquire();
                    if (followers > 0) {
                        followers--;
                        followerQueue.release();
                    } else {
                        leaders++;
                        mutex.release();
                        leaderQueue.acquire();
                    }
                    dance("leader");
                    mutex.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

            /**
             * Follower section:
             * When a follower arrives it acquires the mutex
             * If there is a leader waiting, the follower decrements the leader count and releases the leaderQueue for another leader
             * The leader and follower proceed to dance and release the mutex lock for another thread
             *
             * If there is no leader waiting, the follower the acquires the followerQueue but gives up the mutex to wait on a leader
             */
            executor.submit(() -> {
                try {
                    mutex.acquire();
                    if (leaders > 0) {
                        leaders--;
                        leaderQueue.release();
                    } else {
                        followers++;
                        mutex.release();
                        followerQueue.acquire();
                    }
                    dance("follower");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }
    /**
     * Dance method
     * @param type - either leader or follower
     */
    private void dance(String type) {
        System.out.println("Generated " + type);
    }

    public static void main(String[] args) {
        /**
         * Create a queue object
         * Call the leaderFollower() function
         */
        Queue queue = new Queue();
        queue.leaderFollower();
    }
}
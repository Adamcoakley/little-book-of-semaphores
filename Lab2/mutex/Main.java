/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lab2.mutex;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author  Adam Coakley
 * @since   2021-10-11
 *
 */

public class Main {

    /**
     * MAX_T = max number of threads in the thread pool
     */
    static final int MAX_T = 4;
  
    public static void main(String[] args)
    {
        IntegerObj total= new IntegerObj(0);
        /**
         * Create five tasks
         */
        Runnable r1 = new Task("task 1",total);
        Runnable r2 = new Task("task 2",total);
        Runnable r3 = new Task("task 3",total);
        Runnable r4 = new Task("task 4",total);

        /**
         * Create a thread pool with MAX_T number pf threads
         */
        ExecutorService pool = Executors.newFixedThreadPool(MAX_T);

        /**
         * Uses the execute function to run each Task
         */
        pool.execute(r1);
        pool.execute(r2);
        pool.execute(r3);
        pool.execute(r4);

        /**
         * Shutdown the thread pool
         */
        pool.shutdown();    
        try {
            Thread.sleep(2500);
        } catch (InterruptedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        /**
         * Prints out the sum of each thread
         */
        System.out.println("total is: "+total.value);
    }
}

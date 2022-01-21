/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Lab1.RunnableDemo;

/**
 *
 * @author Adam Coakley
 * @since   2021-10-11
 *
 */

class RunnableDemo implements Runnable {

   /**
    * Every thread is given a name
    */
   private Thread t;
   private String threadName;
   
   RunnableDemo(String name) {
      threadName = name;
      System.out.println("Creating " +  threadName );
   }

   /**
    * Prints out each threads name that is running
    */
   @Override
   public void run() {
      System.out.println("Running " +  threadName );
      try {
         for(int i = 4; i > 0; i--) {
            System.out.println("Thread: " + threadName + ", " + i);
            /**
             * Lets each thread sleep for 50 millis
             */
            Thread.sleep(50);
         }
      } catch (InterruptedException e) {
         System.out.println("Thread " +  threadName + " interrupted.");
      }
      System.out.println("Thread " +  threadName + " exiting.");
   }

   /**
    * Starts each thread and prints their name
    */
   public void start () {
      System.out.println("Starting " +  threadName );
      if (t == null) {
         t = new Thread (this, threadName);
         t.start ();
      }
   }
}

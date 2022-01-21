/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lab2.mutex;

import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author  Adam Coakley
 * @since   2021-10-11
 *
 */

class IntegerObj {

    /**
     * Initialise a mutex lock
     */
    private ReentrantLock mutex = new ReentrantLock();

    /**
     * Initialise an integer
     */
    int value;

    /**
     * Constructor for IntegerObj
     * @param val the value of the integer
     */
    IntegerObj(int val) {
        this.value = val;
    }

    /**
     * a function to increment the value of the integer
     * a mutex lock is used to protect the critical section (this.value++)
     * @return the new value of the incremented integer
     */
    int inc(){
        try {
            mutex.lock();
            this.value++;
            return this.value;
        } finally {
            mutex.unlock();
        }
    }
}

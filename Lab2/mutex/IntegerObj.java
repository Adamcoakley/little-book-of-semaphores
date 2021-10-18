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

    private ReentrantLock mutex = new ReentrantLock();

    int value;
    IntegerObj(int val) {
        this.value = val;
    }
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

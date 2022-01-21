/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lab2.atomicInteger;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author  Adam Coakley
 * @since   2021-10-11
 *
 */

class IntegerObj {

    /**
     * Create an atomic integer
     */
    AtomicInteger value;

    /**
     * Constructor for IntegerObj
     * @param val the value of the atomic integer
     */
    IntegerObj(AtomicInteger val) {
        this.value = val;
    }
    /**
     * a function to increment the value of the atomic integer
     * @return the new value of the incremented atomic integer
     */
    int inc(){
        return value.incrementAndGet();
    }
}

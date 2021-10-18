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

    AtomicInteger  value;

    IntegerObj(AtomicInteger val) {
        this.value = val;
    }
    int inc(){
        return value.incrementAndGet();
    }
}

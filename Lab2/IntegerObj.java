/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lab2;

/**
 *
 * @author  Adam Coakley
 * @since   2021-10-11
 *
 */

class IntegerObj {

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
     * @return the new value of the incremented integer
     */
    int inc(){
        this.value++;
        return this.value;
    }
}

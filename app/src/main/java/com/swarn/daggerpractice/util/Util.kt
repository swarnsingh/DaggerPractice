package com.swarn.daggerpractice.util

/**
 * @author Swarn Singh.
 */


/**
 *  Extension function to check given String is Integer or not
 */
fun String.isInteger(): Boolean {
    return this.matches("-?\\d+(\\.\\d+)?".toRegex())
}
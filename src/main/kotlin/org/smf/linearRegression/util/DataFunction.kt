package org.smf.linearRegression.util

import org.smf.common.data.Matrix

/**
 * Extension function for converting a double array list to a matrix.
 * A column count must be given.
 */
fun ArrayList<Double>.toMatrix(cols: Int): Matrix {

    if (size > 0 && (size % cols == 0)) {
        return Matrix(size / cols, cols, this)
    }
    throw IllegalArgumentException("No enough elements")

}

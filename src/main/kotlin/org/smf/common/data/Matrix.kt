package org.smf.common.data

/**
 * Matrix implementation
 * All data stored as double array list
 */
data class Matrix(val rows: Int, val cols: Int, val data: ArrayList<Double>) {

    /**
     * Method for retrieve data for the given row and column.
     */
    operator fun get(x: Int, y: Int): Double {
        if (x > rows - 1 || y > (cols - 1)) {
            throw IllegalArgumentException("Invalid index")
        }
        return data[x * cols + y]
    }
}

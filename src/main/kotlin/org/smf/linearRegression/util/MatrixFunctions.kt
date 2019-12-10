package org.smf.linearRegression.util


import org.smf.common.data.Matrix
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * Matrices multiplication (each element)
 */
infix fun Matrix.x(other: Matrix): Matrix {

    if (cols !== other.rows)
        throw IllegalArgumentException("Matrices not match")

    val newRows = rows * other.cols
    val newCols = other.cols
    val newDataList = arrayListOf<Double>()
    repeat((0 until rows).count()) { x ->
        var cellValue = 0.0
        repeat((0 until cols).count()) { y ->
            repeat((0 until other.cols).count()) { z ->
                cellValue += this[x, y] * other[y, z]
            }
        }
        newDataList.add(cellValue)
    }
    return Matrix(newRows, newCols, newDataList)
}

/**
 * Summation of matrix
 */
fun Matrix.sum(): Double {

    var sum = 0.0
    for (i in 0 until this.rows) {
        for (j in 0 until this.cols) {
            sum += this[i, j]
        }
    }
    return sum
}

/**
 * Matrix minus function
 *
 * [Matrix 3] = [Matrix 1] - [Matrix 2]
 */
operator fun Matrix.minus(other: Matrix): Matrix {

    if (rows !== other.rows || cols !== other.cols) {
        throw IllegalArgumentException("Matrices not match")
    }
    return matrixOps(this, other) { a, b -> a - b }
}


/**
 *
 */
operator fun Matrix.div(value: Double): Matrix {
    return matrixOps2(this, value) { a, b -> a / b }
}

/**
 * Matrix times double function
 *
 * [Matrix 2] = [Matrix 1] * Double
 */
operator fun Matrix.times(value: Double): Matrix {
    return matrixOps2(this, value) { a, b -> a * b }
}

/**
 * Matrix power function
 *
 * [Matrix 2] = [Matrix 1] ^ power
 */
fun Matrix.pow(i: Double): Matrix {

    val newDataList = arrayListOf<Double>()
    repeat((0 until rows).count()) { x ->
        repeat((0 until cols).count()) { y ->
            newDataList.add(this[x, y].pow(i))
        }
    }
    return Matrix(rows, cols, newDataList)
}


/**
 *  Insert column for given index with given default value
 */
fun Matrix.insertDefault(defaultValue: Double, colIndex: Int): Matrix {

    val newDataList = arrayListOf<Double>()
    for ((index, value) in data.iterator().withIndex()) {

        if ((index - colIndex) % cols == 0) {
            newDataList.add(defaultValue)
        }
        newDataList.add(value)
    }
    return Matrix(rows, cols + 1, newDataList)
}


/**
 * Matrix transpose
 */
fun Matrix.transpose(): Matrix {

    val newDataList = arrayListOf<Double>()
    repeat((0 until cols).count()) { x ->
        repeat((0 until rows).count()) { y ->
            newDataList.add(this[y, x])
        }
    }
    return Matrix(cols, rows, newDataList)
}


/**
 * Method for calculate mean per column and return as row vector
 */
fun Matrix.mean(): Matrix {
    val newDataList = arrayListOf<Double>()
    repeat((0 until cols).count()) { y ->
        val valueArray = this.toArray(y)
        newDataList.add(valueArray.average())
    }
    return Matrix(1, this.cols, newDataList)
}

/**
 * Method for calculate standard deviation per column and return as row vector
 */
fun Matrix.std(): Matrix {

    val newDataList = arrayListOf<Double>()
    repeat((0 until cols).count()) { y ->
        val valueArray = this.toArray(y)
        val mean = valueArray.average()
        val sd = valueArray.fold(0.0, { accumulator, next -> accumulator + (next - mean).pow(2.0) })
        newDataList.add(sqrt(sd / valueArray.size))
    }
    return Matrix(1, this.cols, newDataList)
}

/**
 * Return specific column's values as double array.
 */
private fun Matrix.toArray(column: Int): ArrayList<Double> {

    val newDataList = arrayListOf<Double>()
    repeat((0 until rows).count()) { x ->
        repeat((0 until cols).count()) { y ->
            if (y == column) {
                newDataList.add(this[x, y])
            }
        }
    }
    return newDataList
}

/**
 * minus operation
 */
infix fun Matrix.columnMinus(other: Matrix): Matrix {
    return columnFunctions(this,other) { a, b -> a - b }
}

/**
 * Division operation
 */
infix fun Matrix.columnDiv(other: Matrix): Matrix {
    return columnFunctions(this,other) { a, b -> a / b }
}


/**
 * Function to execute give function against (n x m) matrix with another (1 x m) matrix (row vector).
 *
 */
private fun columnFunctions(matrix1: Matrix, matrix2: Matrix, operation: (x: Double, y: Double) -> Double): Matrix {

    if (matrix1.cols !== matrix2.cols && matrix2.rows == 1) {
        throw IllegalArgumentException("Matrices not match")
    }
    val newDataList = arrayListOf<Double>()
    repeat((0 until matrix1.rows).count()) { x ->
        repeat((0 until matrix1.cols).count()) { y ->
            newDataList.add(operation(matrix1[x, y], matrix2[0, y]))
        }
    }
    return Matrix(matrix1.rows, matrix1.cols, newDataList)
}

/**
 *
 */
private fun matrixOps(matrix1: Matrix, matrix2: Matrix, operation: (x: Double, y: Double) -> Double): Matrix {
    val newDataList = arrayListOf<Double>()
    repeat((0 until matrix1.rows).count()) { x ->
        repeat((0 until matrix1.cols).count()) { y ->
            newDataList.add(operation(matrix1[x, y], matrix2[x, y]))
        }
    }
    return Matrix(matrix1.rows, matrix1.cols, newDataList)
}


/**
 *
 */
private fun matrixOps2(matrix1: Matrix, value: Double, operation: (x: Double, y: Double) -> Double): Matrix {

    val newDataList = arrayListOf<Double>()
    repeat((0 until matrix1.rows).count()) { x ->
        repeat((0 until matrix1.cols).count()) { y ->
            newDataList.add(operation(matrix1[x, y], value))
        }
    }
    return Matrix(matrix1.rows, matrix1.cols, newDataList)
}


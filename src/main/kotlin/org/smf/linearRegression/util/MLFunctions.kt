package org.smf.linearRegression.util


import org.smf.common.data.Matrix
import kotlin.math.pow

/**
 * None Vectorized Implementation
 */
fun computeCost(X: Matrix, y: Matrix, theta: Matrix): Double {

    val m = y.rows
    val hypothesis = X x theta

    var temp = 0.0

    repeat((0 until m).count()) {

        temp += (hypothesis[it, 0] - y[it, 0]).pow(2)
    }

    return (1.0 / (2 * m)) * temp
}


/**
 * Vectorized Implementation
 */
fun computeCostVector(X: Matrix, Y: Matrix, theta: Matrix): Double {

    val m = Y.rows

    val h = X x theta

    val squaredErrors = (h - Y).pow(2.0)

    return (1.0 / (2 * m)) * squaredErrors.sum()
}

/**
 * Method for normalized features
 */
fun featureNormalize(X: Matrix): NormalizedData {
    val mean = X.mean()
    val sigma = X.std()
    return NormalizedData((X columnMinus mean) columnDiv sigma, mean, sigma)
}

/**
 *
 */
data class NormalizedData(val normalize: Matrix, val mean: Matrix, val std: Matrix)

/**
 *
 */

fun gradientDescent(X: Matrix, y: Matrix, theta: Matrix, learning_rate: Double, iteration: Int): DataCollection {
    val m = y.rows
    var newTheta = theta
    val historyList = arrayListOf<Matrix>()

    val iterator = (0 until iteration).iterator()

    for (value in iterator) {

        val hypothesis = X x newTheta
        /**
         * Q = Q - (learning rate * 1/m) * (hQ - y)X
         */
        newTheta -= (X.transpose() x (hypothesis - y)) * (learning_rate / m)

        historyList.add(newTheta)

    }
    return DataCollection(newTheta, historyList)
}

/**
 *
 */
data class DataCollection(val finalTheta: Matrix, val history: ArrayList<Matrix>)

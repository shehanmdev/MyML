package org.smf.linearRegression

import org.smf.linearRegression.util.*
import java.nio.file.Files
import java.nio.file.Paths

fun main() {

    val resources = Thread.currentThread().contextClassLoader.getResource("ex1data1.txt")
    val results = arrayListOf<Double>()
    val features = arrayListOf<Double>()
    Files.lines(Paths.get(resources.toURI())).map {
        it.split(",")
    }.peek {

        results.add(it.last().toDouble())
        features.addAll(it.subList(0, it.lastIndex).map { str ->
            str.toDouble()
        })
    }.count()

    val featuresConsidering = 1
    val resultMatrix = results.toMatrix(1)
    var featureMatrix = features.toMatrix(featuresConsidering)
    featureMatrix = featureMatrix.insertDefault(1.0, 0)

    val theta = arrayListOf(0.0, 0.0).toMatrix(1)
    println(computeCost(featureMatrix,resultMatrix,theta))
    println(computeCostVector(featureMatrix,resultMatrix,theta))
}

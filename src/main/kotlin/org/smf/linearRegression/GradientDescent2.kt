package org.smf.linearRegression

import org.math.plot.Plot2DPanel
import org.smf.linearRegression.util.*
import java.awt.Color
import java.nio.file.Files
import java.nio.file.Paths
import java.util.concurrent.TimeUnit
import javax.swing.JFrame
import javax.swing.JFrame.EXIT_ON_CLOSE
import kotlin.system.measureTimeMillis

fun main() {

    val resources = Thread.currentThread().contextClassLoader.getResource("ex1data2.txt")

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

    val featuresConsidering = 2
    val resultMatrix = results.toMatrix(1)
    var featureMatrix = features.toMatrix(featuresConsidering)

    val normalizedData = featureNormalize(featureMatrix)
    println(normalizedData.mean)
    println(normalizedData.std)

    featureMatrix = normalizedData.normalize

    featureMatrix = featureMatrix.insertDefault(1.0, 0)

    val theta = arrayListOf(0.0,0.0,0.0).toMatrix(1)

    val learningRate = 0.01
    val iterations = 5000

    var dataCollection: DataCollection? = null
    val timeMillis = measureTimeMillis {
        dataCollection = gradientDescent(featureMatrix, resultMatrix, theta, learningRate, iterations)
    }

    println("Time Taken :-   ${TimeUnit.MILLISECONDS.toMinutes(timeMillis)}" )

    val plot = Plot2DPanel()

    var intercept = dataCollection!!.history.map {
        it[0, 0]
    }.filter { java.lang.Double.isFinite(it) }.toDoubleArray()

    var f1cof = dataCollection!!.history.map {
        it[1, 0]
    }.filter { java.lang.Double.isFinite(it) }.toDoubleArray()

    var f2cof = dataCollection!!.history.map {
        it[2, 0]
    }.filter { java.lang.Double.isFinite(it) }.toDoubleArray()




    intercept = intercept.sliceArray(0 until f1cof.size)

    f1cof = f1cof.sliceArray(0 until intercept.size)

    f2cof = f2cof.sliceArray(0 until f1cof.size)

    val iter = (0 until intercept.size).map {
        it.toDouble()
    }.toDoubleArray()

    plot.addLinePlot("theta0", Color.RED, iter, intercept)
    plot.addLinePlot("theta1", Color.BLUE, iter, f1cof)
    plot.addLinePlot("theta1", Color.GREEN, iter, f2cof)

    println(dataCollection!!.finalTheta)
    println(computeCostVector(featureMatrix, resultMatrix, dataCollection!!.finalTheta))

    val frame = JFrame("MY ML")
    frame.contentPane = plot
    frame.setSize(600, 600)
    frame.defaultCloseOperation = EXIT_ON_CLOSE
    frame.extendedState = frame.extendedState or JFrame.MAXIMIZED_BOTH
    frame.isVisible = true

}

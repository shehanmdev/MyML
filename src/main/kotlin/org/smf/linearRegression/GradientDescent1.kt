package org.smf.linearRegression

import org.math.plot.Plot2DPanel
import org.smf.linearRegression.util.*
import java.awt.Color
import java.nio.file.Files
import java.nio.file.Paths
import javax.swing.JFrame
import javax.swing.JFrame.EXIT_ON_CLOSE

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

    val learningRate = 0.01
    val iterations = 1500

    val dataCollection = gradientDescent(featureMatrix, resultMatrix, theta, learningRate, iterations)

    println(dataCollection.finalTheta)
    println(computeCostVector(featureMatrix, resultMatrix, dataCollection.finalTheta))


    val plot = Plot2DPanel()
    val x = features.toDoubleArray()
    val y = results.toDoubleArray()
    plot.addScatterPlot("", Color.BLUE, x, y)
    val frame = JFrame("MY ML")
    frame.contentPane = plot
    frame.setSize(600, 600)
    frame.defaultCloseOperation = EXIT_ON_CLOSE
    frame.extendedState = frame.extendedState or JFrame.MAXIMIZED_BOTH
    frame.isVisible = true

    val hypothesis = featureMatrix x dataCollection.finalTheta
    plot.addLinePlot("Final", x, hypothesis.data.toDoubleArray())

//    Thread.sleep(3000)
//    var plotId = 0
//    for ((index, value) in dataCollection.history.withIndex()) {
//        if (index != 0) {
//            plot.removePlot(plotId)
//        }
//        val hypothesis = featureMatrix x value
//        plotId = plot.addLinePlot("Final", x, hypothesis.data.toDoubleArray())
//        Thread.sleep(5)
//    }
//    val featureNormalize = featureNormalize(featureMatrix)
}

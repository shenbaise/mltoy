package org.mltoy.model.classification

import scala.collection.immutable.HashMap
import org.mltoy.model.AbstractParams
import org.apache.log4j.{ Level, Logger }
import org.apache.spark.{ SparkConf, SparkContext }
import org.apache.spark.mllib.classification.{ LogisticRegressionWithLBFGS, SVMWithSGD }
import org.apache.spark.mllib.evaluation.BinaryClassificationMetrics
import org.apache.spark.mllib.util.MLUtils
import org.apache.spark.mllib.optimization.{ SquaredL2Updater, L1Updater }
import org.apache.hadoop.yarn.webapp.view.DefaultPage
import org.apache.spark.mllib.classification.LogisticRegressionModel

/**
 * @author tack
 *
 */

object Algorithm extends Enumeration {
    type Algorithm = Value
    val SVM, LR = Value
}

object RegType extends Enumeration {
    type RegType = Value
    val L1, L2 = Value
}
import Algorithm._
import RegType._
case class Params(
    input: String = "data/mllib/sample_binary_classification_data.txt",
    numIterations: Int = 10,
    stepSize: Double = 0.1,
    algorithm: Algorithm = LR,
    regType: RegType = L2,
    regParam: Double = 0.01,
    masterUrl: String = "local[2]") extends AbstractParams[Params]

class BinaryClassification {

    def run(params: Params) {
        val conf = new SparkConf().setAppName(s"BinaryClassification with $params").setMaster(params.masterUrl)
        val sc = new SparkContext(conf)

        Logger.getRootLogger.setLevel(Level.WARN)

        val examples = MLUtils.loadLibSVMFile(sc, params.input).cache()

        val splits = examples.randomSplit(Array(0.8, 0.2))
        val training = splits(0).cache()
        val test = splits(1).cache()

        val numTraining = training.count()
        val numTest = test.count()
        println(s"Training: $numTraining, test: $numTest.")

        examples.unpersist(blocking = false)

        val updater = params.regType match {
            case L1 => new L1Updater()
            case L2 => new SquaredL2Updater()
        }

        val model = params.algorithm match {
            case LR =>
                val algorithm = new LogisticRegressionWithLBFGS()
                algorithm.optimizer
                    .setNumIterations(params.numIterations)
                    .setUpdater(updater)
                    .setRegParam(params.regParam)
                //        algorithm.run(training).clearThreshold()
                var m = algorithm.run(training)
                println(m.getThreshold)
                m
            case SVM =>
                val algorithm = new SVMWithSGD()
                algorithm.optimizer
                    .setNumIterations(params.numIterations)
                    .setStepSize(params.stepSize)
                    .setUpdater(updater)
                    .setRegParam(params.regParam)
                algorithm.run(training).clearThreshold()
        }

        val prediction = model.predict(test.map(_.features))

        val predictionAndLabel = prediction.zip(test.map(_.label))

        predictionAndLabel.foreach(a => println(s"${a._1} ${a._2}"))
        val metrics = new BinaryClassificationMetrics(predictionAndLabel)

        println(s"Test areaUnderPR = ${metrics.areaUnderPR()}.")
        println(s"Test areaUnderROC = ${metrics.areaUnderROC()}.")

        sc.stop()
    }
}


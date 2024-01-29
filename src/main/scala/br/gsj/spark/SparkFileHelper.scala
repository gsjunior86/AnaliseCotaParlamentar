package br.gsj.spark

import org.apache.spark.sql.{Dataset, Encoder, Encoders, SparkSession}

object SparkFileHelper {

  private val csvOpts = Map("inferSchema" -> "true", "delimiter" -> ";", "header" -> "true")

  def readCsvFile[T](path: String)(implicit sparkSession: SparkSession, encoder: Encoder[T]): Dataset[T] = {
    sparkSession.read.options(csvOpts).csv(path).as[T]
  }


}

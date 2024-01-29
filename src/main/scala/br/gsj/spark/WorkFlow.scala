package br.gsj.spark

import org.apache.spark.sql.SparkSession

trait WorkFlow {

  lazy val sparkConfig: Map[String,Any] = Map()
  private[spark] lazy val sparkSession: SparkSession = SparkSession.builder().master("local[*]").config(sparkConfig).getOrCreate()

  def run(args: Array[String]): Unit

}

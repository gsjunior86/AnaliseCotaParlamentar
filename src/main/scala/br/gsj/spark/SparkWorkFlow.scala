package br.gsj.spark
import SparkFileHelper._

trait SparkWorkFlow extends WorkFlow {

  def readData(): Unit = {
    sparkSession
  }

  def main(args: Array[String]): Unit = {
    run(args)
  }


}

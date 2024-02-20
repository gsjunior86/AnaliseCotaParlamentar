package br.gsj.spark
import SparkFileHelper._

trait SparkWorkFlow extends WorkFlow {

  def main(args: Array[String]): Unit = {
    run(args)
  }

}

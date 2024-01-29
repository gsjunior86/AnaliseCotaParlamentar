package br.gsj.camara.analise

import br.gsj.camara.spark.SparkBase
import br.gsj.spark.{SparkWorkFlow, WorkFlow}
import br.gsj.utils.DBCmdParser

class AnaliseCota extends WorkFlow with DBCmdParser{

  override def run(args: Array[String]) = {

    val config = parseArgs(args)
  }
}

object AnaliseCota extends SparkWorkFlow {
  override def run(args: Array[String]) = new AnaliseCota().run(args)
}

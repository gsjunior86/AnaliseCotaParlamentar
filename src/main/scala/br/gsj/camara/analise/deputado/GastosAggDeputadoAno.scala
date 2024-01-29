package br.gsj.camara.analise.deputado

import br.gsj.spark.{SparkWorkFlow, WorkFlow}

class GastosAggDeputadoAno extends WorkFlow{
  override def run(args: Array[String]): Unit = {

  }
}


object GastosAggDeputadoAno extends SparkWorkFlow{
  override def run(args: Array[String]): Unit = new GastosAggDeputadoAno().run(args)
}

package br.gsj.camara.main

import br.gsj.camara.analise.{GastosAggDeputadoAnoDashboard, GastosPorAnoDashBoard}
import br.gsj.camara.spark.SparkBase

object Main {

  def main(args: Array[String]): Unit = {

    GastosPorAnoDashBoard.run()
    //GastosAggDeputadoAnoDashboard.run()
  }

}

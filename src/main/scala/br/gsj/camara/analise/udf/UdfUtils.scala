package br.gsj.camara.analise.udf

import org.apache.spark.sql.functions.udf

object UdfUtils {

  private val extrairNomeMes = (numMes: Int) => {
    numMes match {
      case 1  => "Janeiro"
      case 2  => "Fevereiro"
      case 3  => "Março"
      case 4  => "Abril"
      case 5  => "Maio"
      case 6  => "Junho"
      case 7  => "Julho"
      case 8  => "Agosto"
      case 9  => "Setembro"
      case 10 => "Outubro"
      case 11 => "Novembro"
      case 12 => "Dezembro"
    }
  }

  val extrairNomeMesUDF = udf(extrairNomeMes)

}

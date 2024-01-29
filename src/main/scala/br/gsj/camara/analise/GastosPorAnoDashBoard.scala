package br.gsj.camara.analise

import org.apache.spark.sql.functions.{col, sum}
import org.apache.spark.sql.types.DecimalType
import br.gsj.camara.analise.udf.UdfUtils._
import br.gsj.camara.spark.SparkBase

object GastosPorAnoDashBoard extends SparkBase {

  val csvPath = sys.env("CSV_PATH") + "*.csv"

  val options = Map("inferSchema" -> "true", "delimiter" -> ";", "header" -> "true")

  val altSchema = "gasto_por_ano"

  def run(): Unit = {

    val cotaDF = readDataFrame(csvPath, options)
      .where(col("ideCadastro").isNotNull && col("numAno") > 0)

    /*
    agregar os gastos por mês e ano, de forma a ordenar os meses em que mais se gastou
     */

    val gastosMesAno = cotaDF
      .groupBy(col("numMes"), col("numAno"))
      .agg(
        sum(col("vlrLiquido"))
          .cast(DecimalType(10, 0))
          .as("total_gasto")
      )
      .orderBy(col("numAno").desc, col("numMes").desc)
      .withColumn("nomeMes", extrairNomeMesUDF(col("numMes")))
      .drop(col("numMes"))

    val gastoPartidoAno = cotaDF
      .groupBy(col("sgPartido"), col("numAno"))
      .agg(
        sum(col("vlrLiquido"))
          .cast(DecimalType(10, 0))
          .as("total_gasto")
      )
      .orderBy(col("sgPartido").desc, col("total_gasto").desc)

    val gastosTipoAno = cotaDF
      .groupBy(col("txtDescricao"), col("numAno"))
      .agg(
        sum(col("vlrLiquido"))
          .cast(DecimalType(10, 0))
          .as("total_gasto")
      )
      .orderBy(col("txtDescricao").desc, col("total_gasto").desc)

    saveDataFrame(dataFrame = gastosMesAno, tableName = "gastos_mes_ano", altSchema = Some(altSchema))
    saveDataFrame(dataFrame = gastoPartidoAno, tableName = "gastos_partido_ano", altSchema = Some(altSchema))
    saveDataFrame(dataFrame = gastosTipoAno, tableName = "gastos_tipo_ano", altSchema = Some(altSchema))

  }

}

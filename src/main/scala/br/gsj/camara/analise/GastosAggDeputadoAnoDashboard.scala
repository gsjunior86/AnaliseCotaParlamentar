package br.gsj.camara.analise

import br.gsj.camara.analise.udf.UdfUtils.extrairNomeMesUDF
import br.gsj.camara.spark.SparkBase
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.DecimalType

object GastosAggDeputadoAnoDashboard extends SparkBase {

  val csvPath = sys.env("CSV_PATH") + "*.csv"

  val options = Map("inferSchema" -> "true", "delimiter" -> ";", "header" -> "true")

  val altSchema = "gasto_por_deputado_ano"

  def run(): Unit = {

    val cotaDF = readDataFrame(csvPath, options)
      .where(col("ideCadastro").isNotNull && col("numAno") > 0)

    val deputadosDF = readDataFrame(sys.env("CSV_PATH") + "deputados.csv", options)

    val gastosMesAno = cotaDF
      .groupBy(col("ideCadastro"), col("numMes"), col("numAno"))
      .agg(
        sum(col("vlrLiquido"))
          .cast(DecimalType(10, 0))
          .as("total_gasto")
      )
      .orderBy(col("numAno").desc, col("numMes").desc)
      .withColumn("nomeMes", extrairNomeMesUDF(col("numMes")))

    val gastosTipoAno = cotaDF
      .groupBy(col("ideCadastro"), col("txtDescricao"), col("numAno"))
      .agg(
        sum(col("vlrLiquido"))
          .cast(DecimalType(10, 0))
          .as("total_gasto")
      )
      .orderBy(col("txtDescricao").desc, col("total_gasto").desc)

    cotaDF.show

  }

}

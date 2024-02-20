package br.gsj.camara.analise.deputado

import br.gsj.camara.helpers.CamaraHelper.DespesaCotaParlamentar
import br.gsj.camara.helpers.{CamaraDataset, FileReaderHelper}
import br.gsj.spark.{SparkWorkFlow, WorkFlow}

class GastosAggDeputadoAno(fileReaderHelper: FileReaderHelper) extends WorkFlow {

  import spark.implicits._

  override def run(args: Array[String]): Unit = {
    val gastosDF =
      fileReaderHelper.readDataSet(CamaraDataset.DespesaCotaParlamentar, Some(2013)).as[DespesaCotaParlamentar]
  }
}

object GastosAggDeputadoAno extends SparkWorkFlow {
  override def run(args: Array[String]): Unit =
    new GastosAggDeputadoAno(new FileReaderHelper(spark)).run(args)
}

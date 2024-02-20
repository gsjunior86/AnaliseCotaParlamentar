package br.gsj.camara.helpers

import com.typesafe.config.{Config, ConfigFactory}
import org.apache.spark.sql.{DataFrame, SparkSession}

import java.io.{BufferedOutputStream, FileInputStream, FileOutputStream}
import java.net.URL
import java.nio.file.{Files, Paths, StandardCopyOption}
import java.util.zip.{ZipEntry, ZipInputStream}

object CamaraDataset extends Enumeration {
  type CamaraDataset = Value

  val DeputadoDS = Value("app.dataset.deputados")
  val DespesaCotaParlamentar = Value("app.dataset.cotaParlamentar")
}

class FileReaderHelper(spark: SparkSession) {

  import CamaraDataset._
  private val applicationConf: Config = ConfigFactory.load("datasets.conf")
  private val downloadPath = applicationConf.getString("app.conf.downloadFolder")
  private val csvOpts = Map[String, String]("header" -> "true", "delimiter" -> ";")

  /** Reads a dataset for a given CamaraDataset
    * @param ds the desired dataset
    * @param ano the year of the dataset, in case it is applied
    * @return
    */
  def readDataSet(ds: CamaraDataset, ano: Option[Int] = None): DataFrame = {

    ds match {
      case DespesaCotaParlamentar =>
        if (!ano.isEmpty)
          downloadAndReadDataset(
            applicationConf.getString(CamaraDataset.DespesaCotaParlamentar.toString).replace("XXXX", ano.get.toString)
          )
        else throw new Exception("O Ano é obrigatório para Cota Parlamentar")
      case _ => downloadAndReadDataset(CamaraDataset.DespesaCotaParlamentar.toString)
    }

  }

  /** Download and read the CSV dataset from an URL
    * @param url
    */

  private def downloadAndReadDataset(url: String): DataFrame = {
    var fileName = url.substring(url.lastIndexOf("/") + 1)
    val sourceUrl = new URL(url)
    val destinationFile = Paths.get(s"${downloadPath}/${fileName}")
    Files.copy(sourceUrl.openStream(), destinationFile, StandardCopyOption.REPLACE_EXISTING)
    if (fileName.endsWith(".zip")) {
      decompress(
        zipFilePath = downloadPath + s"/${fileName}",
        outputDirectory = downloadPath
      )
      fileName = fileName.replaceAll(".zip", "")
    }

    spark.read.options(csvOpts).csv(s"${downloadPath}/${fileName}")

  }

  /** Decompress ZIP Files
    * @param zipFilePath the path where the ZIP file is located
    * @param outputDirectory the path where will be extracted
    */
  private def decompress(zipFilePath: String, outputDirectory: String): Unit = {
    val buffer = new Array[Byte](1024)

    try {
      val zipInputStream = new ZipInputStream(new FileInputStream(zipFilePath))

      var entry: ZipEntry = zipInputStream.getNextEntry
      while (entry != null) {
        val entryFileName = entry.getName
        val entryPath = outputDirectory + java.io.File.separator + entryFileName

        if (entry.isDirectory) {
          new java.io.File(entryPath).mkdirs()
        } else {
          val outputStream = new BufferedOutputStream(new FileOutputStream(entryPath))
          var len = 0

          while ({ len = zipInputStream.read(buffer); len } > 0) {
            outputStream.write(buffer, 0, len)
          }

          outputStream.close()
        }

        entry = zipInputStream.getNextEntry
      }

      zipInputStream.closeEntry()
      zipInputStream.close()
      println(s"Arquivo Zip descompactado com sucesso para: $outputDirectory")
    } catch {
      case e: Exception =>
        println(s"Erro ao descompactar arquivo zip: ${e.getMessage}")
    }
  }

}

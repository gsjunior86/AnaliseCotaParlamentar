package br.gsj.camara.spark

import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}

trait SparkBase {

  val DB_HOST = sys.env("DB_HOST")
  val DB_USER = sys.env("DB_USER")
  val DB_PASSWD = sys.env("DB_PASSWD")
  val SOURCE_DB = sys.env("SOURCE_DB")
  val SCHEMA_DB = sys.env("SCHEMA_DB")

  val driver = sys.env.getOrElse(
    "DRIVER_CLASS",
    throw new NoSuchElementException("DRIVER_CLASS must be set")
  )

  private lazy val spark = SparkSession
    .builder()
    .master("local[*]")
    .appName("AnaliseCotaParlamentar")
    .getOrCreate()

  println("SPARK SESSION IS " + spark == null)

  val saveMode = SaveMode.Overwrite

  /*Use to read dataframe*/
  def readDataFrame(path: String, options: scala.collection.Map[String, String]): DataFrame = {
    spark.read.options(options).csv(path)
  }

  def saveDataFrame(dataFrame: DataFrame, tableName: String, altSchema: Option[String] = None) = {
    dataFrame.write
      .format("jdbc")
      .mode(saveMode)
      .option("url", s"jdbc:postgresql://${DB_HOST}/${SOURCE_DB}")
      .option("dbtable", if (altSchema.isEmpty) s"${SCHEMA_DB}.${tableName}" else s"${altSchema.get}.${tableName}")
      .option("user", DB_USER)
      .option("password", DB_PASSWD)
      .option("driver", driver)
      .save
  }

  def run(): Unit

}

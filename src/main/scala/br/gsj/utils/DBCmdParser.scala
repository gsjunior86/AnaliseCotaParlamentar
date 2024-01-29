package br.gsj.utils

import scopt.OParser

trait DBCmdParser {

  protected case class DBconfig(
                             file: String ="",
                             db_host: String = "",
                             db_user: String = "",
                             db_password: String = "",
                             db_table: String = ""
                           )

  def parseArgs(args: Array[String]) : DBconfig = {
    val builder = OParser.builder[DBconfig]

    val argParser = {
      import builder._
      OParser.sequence(
        programName("SparkIngestion"),
        head("SparkIngestion", "0.1"),
        opt[String]('f', "file")
          .required()
          .action((f, c) => c.copy(file = f))
          .text("The CSV File"),
        opt[String]('h', "host")
          .required()
          .action((h, c) => c.copy(db_host = h))
          .text("The host address"),
        opt[String]('u', "user")
          .required()
          .action((u, c) => c.copy(db_user = u))
          .text("The DB user"),
        opt[String]('p', "password")
          .required()
          .action((p, c) => c.copy(db_password = p))
          .text("The DB password"),
        opt[String]('t', "table")
          .required()
          .action((t, c) => c.copy(db_table = t))
          .text("The DB table")
        //        checkConfig(c => {
        //          if (c.argA < c.argB) {
        //            success
        //          } else {
        //            failure("just cause")
        //          }
        //        })
      )


    }
    OParser.parse(argParser,args, DBconfig()) match {
      case Some(dbconfig) => dbconfig
      case _ => throw new RuntimeException("Invalid Arguments")
    }

  }

}

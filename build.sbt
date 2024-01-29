lazy val scala213 = "2.13.12"
lazy val sparkVersion3 = "3.5.0"


lazy val root = (project in file("."))
  .settings(
    name := "AnaliseCotaParlamentar"
  )

resolvers += Resolver.mavenLocal
resolvers += Resolver.DefaultMavenRepository
resolvers +=
  "Sonatype OSS Snapshots".at("https://oss.sonatype.org/content/repositories/snapshots")

resolvers +=
  "Sonatype OSS Releases".at("https://oss.sonatype.org/content/repositories/releases")


lazy val sparkDependencies = Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion3,
  "org.apache.spark" %% "spark-sql" % sparkVersion3
)

lazy val dependencies = Seq(
  "com.github.scopt" %% "scopt" % "4.0.1"
)

libraryDependencies ++= sparkDependencies ++ dependencies
//  Seq(
//  "org.postgresql" % "postgresql" % "42.3.6"
//)

ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "br.gsj"
ThisBuild / scalaVersion := scala213

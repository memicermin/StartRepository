import play.ebean.sbt.PlayEbean

name := """CarHouse"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  javaJpa,
  filters,
  "mysql" % "mysql-connector-java" % "5.1.18",
  "org.avaje" % "ebean" % "2.7.3",
  "org.apache.commons" % "commons-email" % "1.3.3",
  "javax.persistence" % "persistence-api" % "1.0.2",
  "com.cloudinary" % "cloudinary" % "1.0.14"
  /*,
  "org.apache.spark" %% "spark-mllib" % "1.4.0"
*/

)
routesGenerator := InjectedRoutesGenerator

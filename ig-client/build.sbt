import sbt.Keys._

name := """ig-client"""

scalaVersion := "2.11.8"
val slf4jVersion = "1.7.21"
val logbackVersion = "1.1.7"

libraryDependencies ++= Seq(
  ws,
  "org.slf4j" % "slf4j-api" % slf4jVersion,
  "ch.qos.logback" % "logback-core" % logbackVersion % Runtime,
  "ch.qos.logback" % "logback-classic" % logbackVersion % Runtime
)

lazy val `ig-client` = (project in file("."))
  .enablePlugins(Common, PlayScala)

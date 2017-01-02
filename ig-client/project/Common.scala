import sbt.Keys._
import sbt._
import sbt.plugins.JvmPlugin

/**
  * Settings that are comment to all the SBT projects
  */
object Common extends AutoPlugin {
  override def trigger = allRequirements

  override def requires: sbt.Plugins = JvmPlugin

  override def projectSettings = Seq(
    organization := "org.abhijitsarkar.ig",
    version := "1.0-SNAPSHOT",
    resolvers += Resolver.typesafeRepo("releases"),
    javacOptions ++= Seq("-source", "1.8", "-target", "1.8"),
    scalacOptions ++= Seq(
      "-encoding",
      "UTF-8", // yes, this is 2 args
      "-target:jvm-1.8",
      "-deprecation",
      "-feature",
      "-unchecked",
      "-Xlint",
      "-Yno-adapted-args",
      // Compile fails on routes file
      // See http://stackoverflow.com/questions/37413032/ywarn-unused-import-triggering-on-play-routes-file
      // "-Ywarn-unused-import",
      "-Ywarn-dead-code",
      "-Ywarn-value-discard",
      "-Ywarn-infer-any",
      "-Ywarn-numeric-widen",
      "-Xfatal-warnings"
    ),
    scalacOptions in Test ++= Seq("-Yrangepos")
  )
}

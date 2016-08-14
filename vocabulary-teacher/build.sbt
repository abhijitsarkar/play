name := "vocabulary-teacher"

version := "1.0.0-SNAPSHOT"

scalaVersion := "2.11.8"

lazy val `vocabulary-teacher` = (project in file(".")).enablePlugins(PlayScala)

routesGenerator := InjectedRoutesGenerator

routesImport += "binders.PathBinders._"
routesImport += "binders.QueryStringBinders._"
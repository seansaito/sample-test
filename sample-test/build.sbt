name := """sample-test"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  ws,
  "com.github.nscala-time" %% "nscala-time" % "2.0.0"
)

PlayKeys.routesImport += "models.PathBinders._"

PlayKeys.routesImport += "java.util.Date"

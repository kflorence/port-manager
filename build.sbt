ThisBuild / compileOrder := CompileOrder.JavaThenScala
ThisBuild / crossScalaVersions := Seq(Versions.scala212, Versions.scala213)
ThisBuild / javacOptions := Seq(
  "-g",
  "-encoding",
  "UTF-8",
  "-source",
  Versions.java,
  "-target",
  Versions.java,
  "-Xlint:unchecked",
  "-Xlint:deprecation"
)
ThisBuild / licenses += ("Apache-2.0", new URL("https://www.apache.org/licenses/LICENSE-2.0.txt"))
ThisBuild / organization := "com.kflorence"
ThisBuild / organizationName := "Kyle Florence"
ThisBuild / scalaVersion := Versions.scala213
ThisBuild / startYear := Some(2021)

lazy val `port-manager-scaladsl` = (project in file("."))
  .enablePlugins(AutomateHeaderPlugin)
  .settings(
    description := "Thread-safe port management within a port bounds.",
    libraryDependencies ++= Seq(Dependencies.scalaTest).map(_ % Test),
    name := "port-manager"
  )

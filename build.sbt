ThisBuild / compileOrder := CompileOrder.JavaThenScala
ThisBuild / crossScalaVersions := Seq(Versions.scala212, Versions.scala213)
ThisBuild / javacOptions := Seq(
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

lazy val javadsl = (project in file("javadsl"))
  .enablePlugins(AutomateHeaderPlugin)
  .settings(
    description := "Thread-safe port reservation and management for Java.",
    name := "port-manager-javadsl"
  )

lazy val scaladsl = (project in file("scaladsl"))
  .dependsOn(javadsl)
  .enablePlugins(AutomateHeaderPlugin)
  .settings(
    description := "Thread-safe port reservation and management for Scala.",
    libraryDependencies ++= Seq(Dependencies.scalaTest).map(_ % Test),
    name := "port-manager-scaladsl"
  )

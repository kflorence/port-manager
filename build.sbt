ThisBuild / crossScalaVersions := Seq(Versions.scala212, Versions.scala213)
ThisBuild / licenses += ("Apache-2.0", new URL("https://www.apache.org/licenses/LICENSE-2.0.txt"))
ThisBuild / organization := "com.kflorence"
ThisBuild / organizationName := "Kyle Florence"
ThisBuild / scalaVersion := Versions.scala213
ThisBuild / startYear := Some(2021)

lazy val `port-manager` = (project in file("."))
  .enablePlugins(AutomateHeaderPlugin)
  .settings(
    description := "Thread-safe port management within a port bounds.",
    libraryDependencies ++= Seq(Dependencies.scalaTest).map(_ % Test),
    name := "port-manager"
  )

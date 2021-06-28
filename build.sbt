ThisBuild / compileOrder := CompileOrder.JavaThenScala
ThisBuild / crossScalaVersions := Seq(Versions.scala212, Versions.scala213)
ThisBuild / developers := List(
  Developer(
    id = "kflorence",
    name = "Kyle Florence",
    email = "kyle.florence@gmail.com",
    url = url("https://kflorence.com")
  )
)
ThisBuild / homepage := Some(url("https://github.com/kflorence/port-manager"))
ThisBuild / javacOptions := Seq("-encoding", "UTF-8", "-source", Versions.java)
ThisBuild / licenses += ("Apache-2.0", new URL("https://www.apache.org/licenses/LICENSE-2.0.txt"))
ThisBuild / organization := "com.kflorence"
ThisBuild / organizationName := "Kyle Florence"
ThisBuild / scalaVersion := Versions.scala213
ThisBuild / startYear := Some(2021)
ThisBuild / versionScheme := Some("early-semver")

// Don't publish the root project
publish / skip := true
sonatypeCredentialHost := Sonatype.sonatype01
sonatypeRepository := "https://s01.oss.sonatype.org/service/local"

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
    libraryDependencies ++= Seq(Dependencies.scalaCollectionCompat) ++ Seq(Dependencies.scalaTest).map(_ % Test),
    name := "port-manager-scaladsl"
  )

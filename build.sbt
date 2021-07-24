inThisBuild(
  List(
    compileOrder := CompileOrder.JavaThenScala,
    crossScalaVersions := Seq(Versions.scala212, Versions.scala213),
    developers := List(
      Developer(
        id = "kflorence",
        name = "Kyle Florence",
        email = "kyle.florence@gmail.com",
        url = url("https://kflorence.com")
      )
    ),
    homepage := Some(url("https://github.com/kflorence/port-manager")),
    javacOptions := Seq("-encoding", "UTF-8", "-source", Versions.java),
    licenses += ("Apache-2.0", new URL("https://www.apache.org/licenses/LICENSE-2.0.txt")),
    organization := "com.kflorence",
    organizationName := "Kyle Florence",
    sonatypeCredentialHost := Sonatype.sonatype01,
    scalaVersion := Versions.scala213,
    startYear := Some(2021),
    versionScheme := Some("early-semver")
  )
)

// https://github.com/olafurpg/sbt-ci-release/issues/173#issuecomment-886082868
lazy val commonSettings = Seq(
  sonatypeCredentialHost := Sonatype.sonatype01
)

lazy val root = (project in file("."))
  .aggregate(javadsl, scaladsl)
  .settings(commonSettings)
  .settings(publish / skip := true)

lazy val javadsl = (project in file("javadsl"))
  .enablePlugins(AutomateHeaderPlugin)
  .settings(commonSettings)
  .settings(
    description := "Thread-safe port reservation and management for Java.",
    name := "port-manager-javadsl"
  )

lazy val scaladsl = (project in file("scaladsl"))
  .dependsOn(javadsl)
  .enablePlugins(AutomateHeaderPlugin)
  .settings(commonSettings)
  .settings(
    description := "Thread-safe port reservation and management for Scala.",
    libraryDependencies ++= Seq(Dependencies.scalaCollectionCompat) ++ Seq(Dependencies.scalaTest).map(_ % Test),
    name := "port-manager-scaladsl"
  )

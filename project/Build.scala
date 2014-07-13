import sbt._
import sbt.Keys._

object TestNGPluginBuild extends Build {
  lazy val root = Project(
    id = "sbt-testng-interface",
    base = file("."),
    settings = Project.defaultSettings ++ commonSettings ++ Seq(
      version := "3.0.2",
      crossScalaVersions := Seq("2.9.3", "2.10.4", "2.11.1"),
      libraryDependencies ++= Seq(
        "org.scala-sbt" % "test-interface" % "1.0" % "provided",
        "org.testng" % "testng" % "6.8.8" % "provided",
        "com.google.inject" % "guice" % "2.0" % "provided")))

  lazy val testNGPlugin = Project(
    id = "sbt-testng-plugin",
    base = file("plugin"),
    settings = Project.defaultSettings ++ commonSettings ++ Seq(
      sbtPlugin := true,
      version := "3.0.2",
      crossScalaVersions := Seq("2.10.4"),
      scalacOptions += "-language:_"))

  lazy val commonSettings: Seq[Setting[_]] = publishSettings ++ Seq(
    organization := "de.johoop",
    scalaVersion := "2.10.4",
    scalacOptions ++= Seq("-unchecked", "-deprecation"))

  lazy val publishSettings: Seq[Setting[_]] = Seq(
    publishTo := Some(Resolver.sbtPluginRepo(if (isSnapshot.value) "snapshots" else "releases")),
    publishMavenStyle := false,
    publishArtifact in Test := false)
}

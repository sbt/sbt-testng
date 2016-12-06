import sbt._
import sbt.Keys._
import bintray.Plugin._
import bintray.Keys._

object TestNGPluginBuild extends Build {
  lazy val root = Project(id = "sbt-testng-interface", base = file("."))
    .settings(commonSettings: _*)
    .settings(
      version := "3.0.3",
      crossScalaVersions := Seq("2.9.3", "2.10.4", "2.11.2"),
      libraryDependencies ++= Seq(
        "org.scala-sbt" % "test-interface" % "1.0" % "provided",
        "org.testng" % "testng" % "6.8.8" % "provided",
        "com.google.inject" % "guice" % "2.0" % "provided"))

  lazy val testNGPlugin = Project(id = "sbt-testng-plugin", base = file("plugin"))
    .settings(commonSettings: _*)
    .settings(
      sbtPlugin := true,
      version := "3.0.3",
      crossScalaVersions := Seq("2.10.4"),
      scalacOptions += "-language:_")

  lazy val commonSettings: Seq[Setting[_]] = publishSettings ++ Seq(
    organization := "de.johoop",
    scalaVersion := "2.10.4",
    scalacOptions ++= Seq("-unchecked", "-deprecation"))

  lazy val publishSettings: Seq[Setting[_]] = bintrayPublishSettings ++ Seq(
    publishMavenStyle := false,
    licenses += ("BSD", url("http://opensource.org/licenses/BSD-3-Clause")),
    repository in bintray := "sbt-plugins",
    bintrayOrganization in bintray := None,
    publishArtifact in Test := false)
}

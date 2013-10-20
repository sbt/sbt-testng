import sbt._
import sbt.Keys._

object TestNGPluginBuild extends Build {
  lazy val root = Project(
    id = "sbt-testng-interface",
    base = file("."),
    settings = Project.defaultSettings ++ commonSettings ++ Seq(
      version := "3.0.0",
      crossScalaVersions := Seq("2.9.3", "2.10.3"),
      libraryDependencies ++= Seq(
        "org.scala-sbt" % "test-interface" % "1.0" % "provided",
        "org.testng" % "testng" % "6.8.5" % "provided",
        "com.google.inject" % "guice" % "2.0" % "provided")))

  lazy val testNGPlugin = Project(
    id = "sbt-testng-plugin",
    base = file("plugin"),
    settings = Project.defaultSettings ++ commonSettings ++ Seq(
      sbtPlugin := true,
      version := "3.0.0",
      crossScalaVersions := Seq("2.10.3"),
      scalacOptions += "-language:_"))

  lazy val commonSettings: Seq[Setting[_]] = publishSettings ++ Seq(
    organization := "de.johoop",
    scalaVersion := "2.10.3",
    scalacOptions ++= Seq("-unchecked", "-deprecation"))

  lazy val publishSettings: Seq[Setting[_]] = Seq(
    publishTo := { 
      val qualifier = "sbt-plugin-" + (if (version.value contains "-SNAPSHOT") "snapshots" else "releases")
      Some(Resolver.url(qualifier, new URL(s"http://repo.scala-sbt.org/scalasbt/$qualifier"))(Resolver.ivyStylePatterns))
    },
    publishMavenStyle := false,
    publishArtifact in Test := false)
}

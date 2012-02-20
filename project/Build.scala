import sbt._
import sbt.Keys._

object TestBenchPluginsBuild extends Build {
  lazy val root = Project(
    id = "testbench-sbt-plugins",
    base = file("."),
    settings = Project.defaultSettings ++ Seq(
      sbtPlugin := true,
      version := "2.0.0-SNAPSHOT",
      organization := "de.johoop",
      scalaVersion := "2.9.1",
      crossPaths := false,
      publishArtifact in (Compile, packageDoc) := false,
      publishTo := Some("Scala Tools Nexus" at "http://nexus.scala-tools.org/content/repositories/releases/"),
      credentials += Credentials(Path.userHome / ".ivy2" / ".credentials"),
      scalacOptions ++= Seq("-unchecked", "-deprecation"),
      libraryDependencies ++= Seq(
        "org.scala-tools.testing" % "test-interface" % "0.5" % "provided",
        "org.testng" % "testng" % "6.4" % "provided",
        "com.google.inject" % "guice" % "2.0" % "provided")
    )
  )
}




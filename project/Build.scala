import sbt._
import sbt.Keys._
import com.typesafe.sbteclipse.plugin.EclipsePlugin.EclipseKeys

object TestBenchPluginsBuild extends Build {
  lazy val root = Project(
    id = "sbt-testng-interface",
    base = file("."),
    settings = Project.defaultSettings ++ commonSettings ++ Seq(
      version := "2.0.0-SNAPSHOT",
      crossScalaVersions := Seq("2.8.0", "2.8.1", "2.9.0", "2.9.1"),
      libraryDependencies ++= Seq(
        "org.scala-tools.testing" % "test-interface" % "0.5" % "provided",
        "org.testng" % "testng" % "6.4" % "provided",
        "com.google.inject" % "guice" % "2.0" % "provided")))
  
  lazy val testNGPlugin = Project(
    id = "sbt-testng-plugin",
    base = file("plugin"),
    settings = Project.defaultSettings ++ commonSettings ++ Seq(
      sbtPlugin := true,
      version := "2.0.0-SNAPSHOT"))
  
  override def settings = super.settings ++ Seq(EclipseKeys.skipParents in ThisBuild := false)
  
  lazy val commonSettings: Seq[Setting[_]] = Seq(
    organization := "de.johoop",
    scalaVersion := "2.9.1",
    publishArtifact in (Compile, packageDoc) := false,
    publishTo := Some("Scala Tools Nexus" at "http://nexus.scala-tools.org/content/repositories/releases/"),
    credentials += Credentials(Path.userHome / ".ivy2" / ".credentials"),
    scalacOptions ++= Seq("-unchecked", "-deprecation"))
}




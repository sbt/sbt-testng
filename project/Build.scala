import sbt._
import sbt.Keys._
import com.typesafe.sbteclipse.plugin.EclipsePlugin.EclipseKeys

object TestNGPluginBuild extends Build {
  lazy val root = Project(
    id = "sbt-testng-interface",
    base = file("."),
    settings = Project.defaultSettings ++ commonSettings ++ Seq(
      version := "2.0.1",
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
      version := "2.0.1"))

  override def settings = super.settings ++ Seq(EclipseKeys.skipParents in ThisBuild := false)

  lazy val commonSettings: Seq[Setting[_]] = publishSettings ++ Seq(
    organization := "de.johoop",
    scalaVersion := "2.9.1",
    scalacOptions ++= Seq("-unchecked", "-deprecation"))

  lazy val publishSettings: Seq[Setting[_]] = Seq(
    publishTo <<= version { v: String =>
      val nexus = "https://oss.sonatype.org/"
      if (v.trim.endsWith("SNAPSHOT")) Some("snapshots" at nexus + "content/repositories/snapshots")
      else Some("releases" at nexus + "service/local/staging/deploy/maven2")
    },
    publishMavenStyle := true,
    publishArtifact in Test := false,
    pomIncludeRepository := { x => false },
    pomExtra := (
      <url>https://www.bitbucket.org/jmhofer/sbt-testng-interface</url>
      <licenses>
        <license>
          <name>Eclipse Public License v1.0</name>
          <url>http://www.eclipse.org/legal/epl-v10.html</url>
          <distribution>repo</distribution>
        </license>
      </licenses>
      <scm>
        <url>https://www.bitbucket.org/jmhofer/sbt-testng-interface</url>
        <connection>scm:hg:https://www.bitbucket.org/jmhofer/sbt-testng-interface</connection>
      </scm>
      <developers>
        <developer>
          <id>johofer</id>
          <name>Joachim Hofer</name>
          <url>http://jmhofer.johoop.de</url>
        </developer>
        <developer>
          <id>asflierl</id>
          <name>Andreas Flierl</name>
          <url>http://flierl.eu</url>
        </developer>
      </developers>))
}

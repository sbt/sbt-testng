
version := "1.0.0"

name := "sbt-testng-interface"

organization := "de.johoop"

scalaVersion := "2.9.1"

crossPaths := false

libraryDependencies ++= Seq(
  "org.scala-tools.testing" % "test-interface" % "0.5",
  "org.testng" % "testng" % "6.2.1")

publishArtifact in (Compile, packageDoc) := false

publishTo := Some("Scala Tools Nexus" at "http://nexus.scala-tools.org/content/repositories/releases/")

credentials += Credentials(Path.userHome / ".ivy2" / ".credentials")


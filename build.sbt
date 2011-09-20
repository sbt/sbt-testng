
version := "1.0.0-SNAPSHOT"

name := "sbt-testng-interface"

organization := "de.johoop"

scalaVersion := "2.9.1"

libraryDependencies ++= Seq(
  "org.scala-tools.testing" % "test-interface" % "0.5",
  "org.testng" % "testng" % "6.2.1")


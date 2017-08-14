val v = "3.0.4-SNAPSHOT"

lazy val root = Project(id = "sbt-testng-interface", base = file("."))
  .settings(commonSettings: _*)
  .settings(
    version := v,
    crossScalaVersions := Seq("2.10.6", "2.11.11", "2.12.3"),
    libraryDependencies ++= Seq(
      "org.scala-sbt" % "test-interface" % "1.0" % "provided",
      "org.testng" % "testng" % "6.9.13.6" % "provided"))

lazy val testNGPlugin = Project(id = "sbt-testng-plugin", base = file("plugin"))
  .settings(commonSettings: _*)
  .settings(
    sbtPlugin := true,
    version := v,
    crossScalaVersions := Seq("2.10.6"),
    scalacOptions += "-language:_")

lazy val commonSettings: Seq[Setting[_]] = publishSettings ++ Seq(
  organization := "de.johoop",
  scalaVersion := "2.10.6",
  scalacOptions ++= Seq("-unchecked", "-deprecation"))

lazy val publishSettings: Seq[Setting[_]] = Seq(
  bintrayOrganization := None,
  bintrayRepository := "sbt-plugins",
  bintrayPackage := name.value,
  publishArtifact in Test := false,
  publishMavenStyle := false,
  licenses += ("BSD", url("http://opensource.org/licenses/BSD-3-Clause"))
)

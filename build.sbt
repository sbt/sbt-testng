val v = "3.1.2-SNAPSHOT"
val testngVersion = SettingKey[String]("testngVersion")
val preCompiledInterfaceVersions = SettingKey[Seq[String]]("preCompiledInterfaceVersions")
val interfaceName = "sbt-testng-interface"

lazy val root = Project(id = interfaceName, base = file("."))
  .settings(commonSettings: _*)
  .settings(
    name := interfaceName,
    version := v,
    crossScalaVersions := Seq("2.11.12", "2.12.12", "2.13.3"),
    libraryDependencies ++= Seq(
      "org.scala-sbt" % "test-interface" % "1.0" % "provided",
      "org.testng" % "testng" % testngVersion.value % "provided"))

lazy val testNGPlugin = Project(id = "sbt-testng-plugin", base = file("plugin"))
  .enablePlugins(BuildInfoPlugin)
  .enablePlugins(SbtPlugin)
  .settings(commonSettings: _*)
  .settings(
    preCompiledInterfaceVersions := (crossScalaVersions in root).value.map(
      CrossVersion.binaryScalaVersion(_)
    ),
    buildInfoKeys := Seq[BuildInfoKey](
      organization,
      version,
      testngVersion,
      preCompiledInterfaceVersions,
      "interfaceName" -> interfaceName
    ),
    buildInfoObject := "TestNGPluginBuildInfo",
    buildInfoPackage := "de.johoop.testngplugin",
    sbtPlugin := true,
    version := v,
    scriptedBufferLog := false,
    scriptedLaunchOpts ++= sys.process.javaVmArguments.filter(
      a => Seq("-Xmx", "-Xms", "-XX", "-Dsbt.log.noformat").exists(a.startsWith)
    ),
    scriptedLaunchOpts += ("-Dplugin.version=" + version.value),
    scalacOptions += "-language:_")

lazy val commonSettings: Seq[Setting[_]] = publishSettings ++ Seq(
  crossSbtVersions := Seq("0.13.18", "1.0.4", "1.1.6", "1.2.8", "1.3.13", "1.4.1"),
  organization := "de.johoop",
  testngVersion := "7.3.0",
  scalacOptions ++= Seq("-unchecked", "-deprecation"))

lazy val publishSettings: Seq[Setting[_]] = Seq(
  bintrayOrganization := Some("sbt"),
  bintrayRepository := "sbt-plugin-releases",
  bintrayPackage := "sbt-testng-plugin-imported",
  publishArtifact in Test := false,
  publishMavenStyle := false,
  licenses += ("BSD", url("http://opensource.org/licenses/BSD-3-Clause"))
)

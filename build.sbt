val v = "3.1.2-SNAPSHOT"
val testngVersion = SettingKey[String]("testngVersion")
val preCompiledInterfaceVersions = SettingKey[Seq[String]]("preCompiledInterfaceVersions")
val interfaceName = "sbt-testng-interface"

lazy val root = Project(id = interfaceName, base = file("."))
  .settings(commonSettings: _*)
  .settings(
    name := interfaceName,
    version := v,
    crossScalaVersions := Seq("2.10.6", "2.11.11", "2.12.3", "2.13.0-M2"),
    libraryDependencies ++= Seq(
      "org.scala-sbt" % "test-interface" % "1.0" % "provided",
      "org.testng" % "testng" % testngVersion.value % "provided"))

lazy val testNGPlugin = Project(id = "sbt-testng-plugin", base = file("plugin"))
  .enablePlugins(BuildInfoPlugin)
  .settings(scriptedSettings)
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
  crossSbtVersions := Seq("0.13.16", "1.0.0"),
  organization := "de.johoop",
  testngVersion := "6.11",
  scalacOptions ++= Seq("-unchecked", "-deprecation"))

lazy val publishSettings: Seq[Setting[_]] = Seq(
  bintrayOrganization := Some("sbt"),
  bintrayRepository := "sbt-plugin-releases",
  bintrayPackage := "sbt-testng-plugin-imported",
  publishArtifact in Test := false,
  publishMavenStyle := false,
  licenses += ("BSD", url("http://opensource.org/licenses/BSD-3-Clause"))
)

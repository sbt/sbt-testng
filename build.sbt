val v = "3.1.2-SNAPSHOT"
val testngVersion = SettingKey[String]("testngVersion")
val preCompiledInterfaceVersions = SettingKey[Seq[String]]("preCompiledInterfaceVersions")
val interfaceName = "sbt-testng-interface"
val scala212 = "2.12.21"
val scala213 = "2.13.18"
val scala3 = "3.8.4"
val repoSlug = "sbt/sbt-testng"

ThisBuild / licenses += ("BSD", url("http://opensource.org/licenses/BSD-3-Clause"))
ThisBuild / organization := "com.github.sbt"
ThisBuild / scalacOptions ++= Seq("-unchecked", "-deprecation")
ThisBuild / testngVersion := "6.11"
ThisBuild / scalaVersion := scala212
ThisBuild / dynverSonatypeSnapshots := true

lazy val `sbt-testng-interface` = (project in file("."))
  .settings(
    name := interfaceName,
    crossScalaVersions := Seq(scala212, scala213, scala3),
    libraryDependencies ++= Seq(
      "org.scala-sbt" % "test-interface" % "1.0" % "provided",
      "org.testng" % "testng" % testngVersion.value % "provided"
    ),
    scalacOptions ++= {
      scalaBinaryVersion.value match {
        case "2.12" | "2.13" =>
          Seq(
            "-Xsource:3",
            "-release:8",
            "-deprecation"
          )
        case "3" =>
          Nil
      }
    },
  )

lazy val `sbt-testng-plugin` = (project in file("plugin"))
  .enablePlugins(BuildInfoPlugin, SbtPlugin)
  .settings(
    crossScalaVersions := Seq(scala212, scala3),
    preCompiledInterfaceVersions := (`sbt-testng-interface` / crossScalaVersions).value.map(
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
    buildInfoPackage := "com.github.sbt.testngplugin",
    scalacOptions ++= {
      scalaBinaryVersion.value match {
        case "2.12" =>
          Seq(
            "-Xsource:3",
            "-release:8",
            "-deprecation",
            "-language:_",
          )
        case "3" =>
          Nil
      }
    },
    pluginCrossBuild / sbtVersion := {
      scalaBinaryVersion.value match {
        case "2.12" => "1.9.0" // set minimum sbt version
        case _      => "2.0.0"
      }
    },
    scriptedSbt := {
      scalaBinaryVersion.value match {
        case "2.12" => "1.13.0"
        case _      => (pluginCrossBuild / sbtVersion).value
      }
    },
    scriptedBufferLog := false,
    scriptedLaunchOpts ++= sys.process.javaVmArguments.filter(
      a => Seq("-Xmx", "-Xms", "-XX", "-Dsbt.log.noformat").exists(a.startsWith)
    ),
    scriptedLaunchOpts += ("-Dplugin.version=" + version.value),
  )

ThisBuild / version := {
  val orig = (ThisBuild / version).value
  if (orig.endsWith("-SNAPSHOT")) v
  else orig
}
ThisBuild / scmInfo := Some(
  ScmInfo(
    url(s"https://github.com/$repoSlug"),
    s"scm:git@github.com:sbt/$repoSlug.git"
  )
)
ThisBuild / developers := List(
  Developer(
    id = "jmhofer",
    name = "Joachim Hofer",
    email = "@jmhofer",
    url = url("http://github.com/jmhofer"),
  ),
  Developer(
    id = "asflierl",
    name = "Andreas Flierl",
    email = "@asflierl",
    url = url("http://github.com/asflierl"),
  ),
  Developer(
    id = "xuwei-k",
    name = "Kenji Yoshida",
    email = "@xuwei-k",
    url = url("http://github.com/xuwei-k"),
  ),
)
ThisBuild / description := "sbt testing interface for TestNG"
ThisBuild / homepage := Some(url(s"https://github.com/$repoSlug"))
ThisBuild / githubWorkflowBuild := Seq(
  WorkflowStep.Sbt(
    List(
      "+sbt-testng-interface/test",
      "+sbt-testng-interface/publishLocal",
      "+sbt-testng-plugin/test",
      "+sbt-testng-plugin/scripted"
    )
  )
)
ThisBuild / githubWorkflowTargetTags ++= Seq("v**")
ThisBuild / githubWorkflowPublishTargetBranches :=
  Seq(
    RefPredicate.StartsWith(Ref.Tag("v"))
  )
ThisBuild / githubWorkflowPublish := Seq(
  WorkflowStep.Sbt(
    commands = List("ci-release"),
    name = Some("Publish project"),
    env = Map(
      "PGP_PASSPHRASE" -> "${{ secrets.PGP_PASSPHRASE }}",
      "PGP_SECRET" -> "${{ secrets.PGP_SECRET }}",
      "SONATYPE_PASSWORD" -> "${{ secrets.SONATYPE_PASSWORD }}",
      "SONATYPE_USERNAME" -> "${{ secrets.SONATYPE_USERNAME }}"
    )
  )
)
ThisBuild / githubWorkflowOSes := Seq("ubuntu-latest", "macos-latest", "windows-latest")
ThisBuild / githubWorkflowPublishJavaVersion := JavaSpec.zulu("8")
ThisBuild / githubWorkflowJavaVersions := Seq(
  JavaSpec.zulu("8")
)

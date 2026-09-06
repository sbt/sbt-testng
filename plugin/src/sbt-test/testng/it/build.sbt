lazy val root = (project in file("."))
  .configs(IntegrationTest)
  .settings(
    Defaults.itSettings
  )

enablePlugins(TestNGItPlugin)

crossScalaVersions := Seq("2.10.6", "2.11.11", "2.12.3")

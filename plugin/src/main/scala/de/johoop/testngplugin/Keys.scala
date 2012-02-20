package de.johoop.testngplugin

import sbt._
import Keys._

trait Keys {
  val testNGVersion = SettingKey[String](
    "testng-version", 
    "the version of TestNG to use")
    
  val testNGOutputDirectory = SettingKey[String](
    "testng-output-directory", 
    "the directory where the test results will be written to by TestNG")
    
  val testNGSuites = SettingKey[Seq[String]](
    "testng-suites", 
    "the suite definition files (YAML or XML) that will be run by TestNG")
}
object Keys extends Keys
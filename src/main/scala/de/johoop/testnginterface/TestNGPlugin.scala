package de.johoop.testnginterface

import sbt._
import sbt.Keys._

object TestNGPlugin extends Plugin with Keys {
  def testNGSettings: Seq[Setting[_]] = Seq(
    testNGVersion := "6.4",
    testNGOutputDirectory <<= (crossTarget)(path => (path / "testng").absolutePath),
    testNGSuites <<= (resourceDirectory in Test)(path => Seq((path / "testng.yaml").absolutePath)),

    libraryDependencies <+= (testNGVersion)("org.testng" % "testng" % _ % "test->default"),
    
    testFrameworks += TestNGFrameworkID,

    testOptions <+= (testNGOutputDirectory, testNGSuites) map { (out, suites) => 
      Tests.Argument(TestNGFrameworkID, "-d", out, suites mkString " ")
    })
    
  object TestNGFrameworkID extends TestFramework("de.johoop.testnginterface.TestNGFramework") { 
    override def toString = "TestNG"
  }
}

/* Copyright (c) 2012-2014 Joachim Hofer & contributors.
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. The names of the author(s) may not be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR(S) ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.github.sbt.testngplugin

import java.io.ByteArrayInputStream
import sbt.*
import sbt.Keys.*

object TestNGPlugin extends AutoPlugin {

  object autoImport {
    val testNGVersion = settingKey[String](
      "the version of TestNG to use")
    val testNGOutputDirectory = settingKey[String](
      "the directory where the test results will be written to by TestNG")
    val testNGParameters = settingKey[Seq[String]](
      "additional parameters to TestNG")
    val testNGSuites = settingKey[Seq[String]](
      "the suite definition files (YAML or XML) that will be run by TestNG")
    val testNGInterfaceVersion = settingKey[String](
      "testngInterfaceVersion")
    val testNGSnakeyamlVersion = settingKey[String](
      "the version of Snakeyaml to use")
  }

  import autoImport.*

  private lazy val testngSources: Array[Byte] = {
    sys.error("unsupported")
    // val artifactId = TestNGPluginBuildInfo.interfaceName + "_2.12"
    // val src = url(s"https://repo.scala-sbt.org/scalasbt/sbt-plugin-releases/${TestNGPluginBuildInfo.organization}/${artifactId}/${TestNGPluginBuildInfo.version}/srcs/${artifactId}-sources.jar")
    // IO.withTemporaryDirectory { dir =>
    //   val f = dir / "temp.jar"
    //   sbt.io.Using.urlInputStream(src) { in =>
    //     IO.transfer(in, f)
    //   }
    //   IO.readBytes(f)
    // }
  }

  override def requires = plugins.JvmPlugin

  override lazy val globalSettings: Seq[Def.Setting[?]] = Seq(
    testNGVersion := TestNGPluginBuildInfo.testngVersion,
    testNGSnakeyamlVersion := "2.2",
    testNGInterfaceVersion := TestNGPluginBuildInfo.version,
    testNGParameters := Seq(),
  )

  override lazy val projectSettings: Seq[Def.Setting[?]] = Seq(
    testNGOutputDirectory := (crossTarget.value / "testng").absolutePath,
    testNGSuites := Seq(((Test / resourceDirectory).value / "testng.yaml").absolutePath),

    libraryDependencies ++= Seq(
      "org.testng" % "testng" % testNGVersion.value % "test->default",
      "org.yaml" % "snakeyaml" % testNGSnakeyamlVersion.value % Test
    ),

    libraryDependencies += {
      if (TestNGPluginBuildInfo.preCompiledInterfaceVersions.contains(scalaBinaryVersion.value)) {
        TestNGPluginBuildInfo.organization %% TestNGPluginBuildInfo.interfaceName % testNGInterfaceVersion.value % "test"
      } else {
        "org.scala-sbt" % "test-interface" % "1.0" % Test
      }
    },

    Test / sourceGenerators += Def.task {
      val dir = (Test / sourceManaged).value
      if (TestNGPluginBuildInfo.preCompiledInterfaceVersions.contains(scalaBinaryVersion.value)) {
        Nil
      } else {
        IO.unzipStream(new ByteArrayInputStream(testngSources), dir).toSeq.filter(_.getName.endsWith("scala"))
      }
    }.taskValue,

    testFrameworks += TestNGFrameworkID,

    testOptions += Tests.Argument(
      TestNGFrameworkID, (("-d" +: testNGOutputDirectory.value +: testNGParameters.value) ++ testNGSuites.value)*
    )
  )

  lazy val TestNGFrameworkID = new TestFramework("com.github.sbt.testnginterface.TestNGFramework")
}

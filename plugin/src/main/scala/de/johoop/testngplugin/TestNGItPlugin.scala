/* Copyright (c) 2018 Joachim Hofer & contributors.
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
package de.johoop.testngplugin

import java.io.ByteArrayInputStream
import sbt._
import sbt.Keys._

object TestNGItPlugin extends BaseTestNGPlugin {

  import autoImport._

  override lazy val projectSettings: Seq[Def.Setting[_]] = Seq(
    resolvers += Resolver.sbtPluginRepo("releases"), // why is that necessary, and why like that?

    testNGVersion := (testNGVersion ?? TestNGPluginBuildInfo.testngVersion).value,
    testNGSnakeyamlVersion := (testNGSnakeyamlVersion ?? "1.17").value,
    testNGInterfaceVersion := (testNGInterfaceVersion ?? TestNGPluginBuildInfo.version).value,
    testNGOutputDirectory := (crossTarget.value / "testng").absolutePath,
    testNGParameters := Seq(),
    testNGSuites := Seq(((resourceDirectory in IntegrationTest).value / "testng.yaml").absolutePath),

    libraryDependencies ++= Seq(
      "org.testng" % "testng" % testNGVersion.value % "test,it",
      "org.yaml" % "snakeyaml" % testNGSnakeyamlVersion.value % "test,it"
    ),

    libraryDependencies += {
      if (TestNGPluginBuildInfo.preCompiledInterfaceVersions.contains(scalaBinaryVersion.value)) {
        TestNGPluginBuildInfo.organization %% TestNGPluginBuildInfo.interfaceName % testNGInterfaceVersion.value % "test,it"
      } else {
        "org.scala-sbt" % "test-interface" % "1.0" % "test,it"
      }
    },

    sourceGenerators in IntegrationTest += Def.task {
      val dir = (sourceManaged in IntegrationTest).value
      if (TestNGPluginBuildInfo.preCompiledInterfaceVersions.contains(scalaBinaryVersion.value)) {
        Nil
      } else {
        IO.unzipStream(new ByteArrayInputStream(testngSources), dir).toList.filter(_.getName endsWith "scala")
      }
    },

    testFrameworks += TestNGFrameworkID,

    testOptions += Tests.Argument(
      TestNGFrameworkID, ("-d" +: testNGOutputDirectory.value +: testNGParameters.value) ++ testNGSuites.value: _*
    )
  )

  lazy val TestNGFrameworkID = new TestFramework("de.johoop.testnginterface.TestNGFramework")

}

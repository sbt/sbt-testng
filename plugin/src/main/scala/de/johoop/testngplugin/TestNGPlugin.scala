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
package de.johoop.testngplugin

import sbt._
import sbt.Keys._

object TestNGPlugin extends Plugin with Keys {
  def testNGSettings: Seq[Setting[_]] = Seq(
	resolvers += Resolver.sbtPluginRepo("releases"), // why is that necessary, and why like that?

    testNGVersion := "6.9.13.6",
    testNGOutputDirectory := (crossTarget.value / "testng").absolutePath,
    testNGParameters := Seq(),
    testNGSuites := Seq(((resourceDirectory in Test).value / "testng.yaml").absolutePath),

    libraryDependencies ++= Seq(
      "org.testng" % "testng" % testNGVersion.value % "test->default",
      "org.yaml" % "snakeyaml" % "1.17" % "test",
      "de.johoop" %% "sbt-testng-interface" % "3.0.3" % "test"),
    
    testFrameworks += TestNGFrameworkID,

    testOptions += Tests.Argument(
      TestNGFrameworkID, ("-d" +: testNGOutputDirectory.value +: testNGParameters.value) ++ testNGSuites.value :_*
    )
  )
    
  object TestNGFrameworkID extends TestFramework("de.johoop.testnginterface.TestNGFramework") {
    override def toString = "TestNG"
  }
}

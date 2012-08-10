/* Copyright (c) 2012 Joachim Hofer & contributors.
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
    testNGVersion := "6.7",
    testNGOutputDirectory <<= (crossTarget)(path => (path / "testng").absolutePath),
    testNGParameters := Seq(),
    testNGSuites <<= (resourceDirectory in Test)(path => Seq((path / "testng.yaml").absolutePath)),

    libraryDependencies <++= (testNGVersion)(v => Seq(
      "org.testng" % "testng" % v % "test->default",
      "de.johoop" %% "sbt-testng-interface" % "2.0.3" % "test")),
    
    testFrameworks += TestNGFrameworkID,

    testOptions <+= (testNGOutputDirectory, testNGParameters, testNGSuites) map { (out, params, suites) => 
      Tests.Argument(TestNGFrameworkID, (("-d" +: out +: params) ++ suites):_*)
    })
    
  object TestNGFrameworkID extends TestFramework("de.johoop.testnginterface.TestNGFramework") {
    override def toString = "TestNG"
  }
}

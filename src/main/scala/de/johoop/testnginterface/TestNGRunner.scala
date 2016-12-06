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

package de.johoop.testnginterface

import org.scalatools.testing.Fingerprint
import org.scalatools.testing.Logger
import org.scalatools.testing.Runner2
import org.scalatools.testing.EventHandler
import TestNGInstance.start

class TestNGRunner(testClassLoader: ClassLoader, loggers: Array[Logger], state: TestRunState) extends Runner2 {
  import state._
  
  def run(testClassname: String, fingerprint: Fingerprint, eventHandler: EventHandler, testOptions: Array[String]) = {
    loggers foreach (_.debug("running for " + testClassname))
    
    if (permissionToExecute.tryAcquire) {
      start(TestNGInstance loggingTo loggers
                           loadingClassesFrom testClassLoader 
                           using testOptions 
                           storingEventsIn recorder)
                           
      testCompletion.countDown()
    }
                           
    testCompletion.await()
    
    recorder.replayTo(eventHandler, testClassname, loggers)
  }
}

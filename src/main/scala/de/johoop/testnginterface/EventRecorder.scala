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

import org.scalatools.testing.Event
import org.testng.ITestResult
import org.testng.TestListenerAdapter
import collection.mutable.HashMap
import org.scalatools.testing.EventHandler
import org.scalatools.testing.Logger
import ResultEvent._

class EventRecorder extends TestListenerAdapter {
  private[this] val basket = HashMap[String, List[Event]]()
  
  override def onTestFailure(result: ITestResult): Unit = store(failure, result)
  override def onTestSkipped(result: ITestResult): Unit = store(skipped, result)
  override def onTestSuccess(result: ITestResult): Unit = store(success, result)
  
  private[this] def store(eventFrom: ITestResult => Event, result: ITestResult): Unit = basket synchronized {
    basket put (classNameOf(result), eventFrom(result) :: basket.getOrElse(classNameOf(result), Nil))
  }
  
  def replayTo(sbt: EventHandler, className: String, loggers: Array[Logger]): Unit = basket synchronized {
    basket remove className getOrElse Nil foreach sbt.handle
  } 
}
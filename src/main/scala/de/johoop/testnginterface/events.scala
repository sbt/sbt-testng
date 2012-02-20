package de.johoop.testnginterface

import org.scalatools.testing.Event
import org.scalatools.testing.Result
import Result._
import org.testng.ITestResult

sealed abstract class AbstractEvent(val result: Result) extends Event {
  def testName = testNGResult getName
  def description = testName
  def error = testNGResult getThrowable
  
  def testNGResult: ITestResult
}

case class FailureEvent(testNGResult: ITestResult) extends AbstractEvent(Failure)
case class SkipEvent(testNGResult: ITestResult) extends AbstractEvent(Skipped)
case class SuccessEvent(testNGResult: ITestResult) extends AbstractEvent(Success)

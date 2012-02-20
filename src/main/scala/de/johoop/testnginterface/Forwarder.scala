package de.johoop.testnginterface

import org.scalatools.testing.EventHandler
import org.testng.ITestResult
import org.testng.TestListenerAdapter

class Forwarder private (target: EventHandler) extends TestListenerAdapter {
  override def onTestFailure(result: ITestResult): Unit = target handle FailureEvent(result)
  override def onTestSkipped(result: ITestResult): Unit = target handle SkipEvent(result)
  override def onTestSuccess(result: ITestResult): Unit = target handle SuccessEvent(result)
}
object Forwarder {
  def to(target: EventHandler): Forwarder = new Forwarder(target)
}
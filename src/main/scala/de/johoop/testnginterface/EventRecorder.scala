package de.johoop.testnginterface

import org.scalatools.testing.Event
import org.testng.ITestResult
import org.testng.TestListenerAdapter
import collection.mutable.HashMap
import org.scalatools.testing.EventHandler
import org.scalatools.testing.Logger

import ResultEvent._

class EventRecorder extends TestListenerAdapter {
  private[this] val basket = HashMap.empty[String, List[Event]]
  
  override def onTestFailure(result: ITestResult): Unit = store(failure(result), result)
  override def onTestSkipped(result: ITestResult): Unit = store(skipped(result), result)
  override def onTestSuccess(result: ITestResult): Unit = store(success(result), result)
  
  private[this] def store(event: Event, result: ITestResult): Unit = {
    val className = result.getTestClass.getName
    
    basket synchronized {
      println("storing " + event + " for " + className)
      basket.put(className, event :: basket.getOrElse(className, List.empty))
    }
  }
  
  def replayTo(sbt: EventHandler, className: String): Unit = eventsFor(className) foreach sbt.handle
  
  private[this] def eventsFor(className: String): List[Event] = basket synchronized {
    println("replaying " + (basket getOrElse (className, List.empty)) + " for " + className)
    basket remove className getOrElse List.empty
  }
}
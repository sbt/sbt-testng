package de.johoop.testnginterface

import org.scalatools.testing.Fingerprint
import org.scalatools.testing.Framework
import org.scalatools.testing.Logger
import org.scalatools.testing.Runner2
import org.scalatools.testing.EventHandler

import java.util.concurrent.Semaphore

import ConfiguredTestNG.start

class TestNGRunner(testClassLoader: ClassLoader, loggers: Array[Logger]) extends Runner2 {
  private val permissionToExecute = new Semaphore(1)
  
  def run(testClassname: String, fingerprint: Fingerprint, eventHandler: EventHandler, testOptions: Array[String]) =
    if (permissionToExecute tryAcquire) start(new ConfiguredTestNG()
                                              loadingClassesFrom testClassLoader 
                                              using testOptions 
                                              forwardingEventsTo eventHandler)
}

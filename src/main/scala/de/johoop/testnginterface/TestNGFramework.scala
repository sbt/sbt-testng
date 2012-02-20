package de.johoop.testnginterface

import org.scalatools.testing.Fingerprint
import org.scalatools.testing.Framework
import org.scalatools.testing.Logger

import java.util.concurrent.Semaphore

class TestNGFramework extends Framework {
  val name = "TestNG"
  def testRunner(testClassLoader: ClassLoader, loggers: Array[Logger]) = new TestNGRunner(testClassLoader, loggers)
  val tests = Array[Fingerprint](AllClassesFingerprint)
}
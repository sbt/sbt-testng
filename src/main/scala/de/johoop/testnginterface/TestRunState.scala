package de.johoop.testnginterface

import java.util.concurrent.Semaphore
import java.util.concurrent.CountDownLatch

class TestRunState {
  val permissionToExecute = new Semaphore(1)
  val testCompletion = new CountDownLatch(1)
  val recorder = new EventRecorder
}
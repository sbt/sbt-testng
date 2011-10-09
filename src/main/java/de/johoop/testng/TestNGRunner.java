package de.johoop.testng;

import static java.util.Arrays.asList;

import org.scalatools.testing.EventHandler;
import org.scalatools.testing.Fingerprint;
import org.scalatools.testing.Logger;
import org.scalatools.testing.Runner2;
import org.testng.TestNG;

public class TestNGRunner extends Runner2 {

  private final ClassLoader testClassLoader;
  private final Logger[] loggers;

  private static final Object lock = new Object();
  private static boolean alreadyRunning = false;
  
  TestNGRunner(ClassLoader testClassLoader, Logger[] loggers) {
    this.testClassLoader = testClassLoader;
    this.loggers = loggers;
  }
  
  @Override
  public void run(final String testClassname, final Fingerprint fingerprint, final EventHandler eventHandler,
      final String[] args) {

    if (isAlreadyRunning()) {
      return;
    }
    
    startTestNGRunner(eventHandler);
  }

  private void startTestNGRunner(final EventHandler eventHandler) {
    System.out.println("starting TestNG runner..."); // FIXME remove me
    
    final TestNG testNG = new TestNG();
    testNG.addClassLoader(testClassLoader);
    testNG.setTestSuites(asList("testng.yaml"));
    testNG.run(); // TODO hook into this somehow

    System.out.println("finished TestNG test run."); // FIXME remove me
}

  private static boolean isAlreadyRunning() {
    synchronized(lock) {
      if (alreadyRunning) {
        return true;
      } else {
        alreadyRunning = true;
        return false;
      }
    }
  }
}

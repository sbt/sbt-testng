package de.johoop.testng;

import org.scalatools.testing.EventHandler;
import org.scalatools.testing.Fingerprint;
import org.scalatools.testing.Runner2;
import org.testng.TestNG;

public class TestNGRunner extends Runner2 {

  private final ClassLoader testClassLoader;

  private static final Object lock = new Object();
  private static boolean alreadyRunning = false;
  
  TestNGRunner(ClassLoader testClassLoader) {
    this.testClassLoader = testClassLoader;
  }
  
  @Override
  public void run(final String testClassname, final Fingerprint fingerprint, final EventHandler eventHandler,
      final String[] testOptions) {

    if (isAlreadyRunning()) {
      return;
    }

    final TestNG testNG = new TestNGConfigurer().forClassLoader(testClassLoader).configure(testOptions);
    startTestNGRunner(testNG, eventHandler);
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

  private void startTestNGRunner(final TestNG testNG, final EventHandler eventHandler) {
    final EventDispatcher dispatcher = new EventDispatcher(eventHandler); 
    testNG.addListener(dispatcher);
    testNG.run();
  }
}

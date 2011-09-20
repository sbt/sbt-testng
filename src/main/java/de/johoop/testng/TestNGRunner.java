package de.johoop.testng;

import org.scalatools.testing.EventHandler;
import org.scalatools.testing.Fingerprint;
import org.scalatools.testing.Logger;
import org.scalatools.testing.Runner;
import org.scalatools.testing.Runner2;
import org.scalatools.testing.TestFingerprint;

public class TestNGRunner extends Runner2 {

  private final ClassLoader testClassLoader;
  private final Logger[] loggers;

  TestNGRunner(ClassLoader testClassLoader, Logger[] loggers) {
    this.testClassLoader = testClassLoader;
    this.loggers = loggers;
  }
  
  @Override
  public void run(final String testClassname, final Fingerprint fingerprint, final EventHandler eventHandler,
      final String[] args) {
    // TODO Auto-generated method stub
  }

}

package de.johoop.testng;

import org.scalatools.testing.Fingerprint;
import org.scalatools.testing.Framework;
import org.scalatools.testing.Logger;
import org.scalatools.testing.Runner;

public class TestNGFramework implements Framework {

  private static Fingerprint[] FINGERPRINTS = new Fingerprint[] { new TestNGFingerprint() };
  
  @Override public String name() {
    return "TestNG";
  }

  @Override public Runner testRunner(final ClassLoader testClassLoader, final Logger[] loggers) {
    return new TestNGRunner(testClassLoader, loggers);
  }

  @Override public Fingerprint[] tests() {
    return FINGERPRINTS;
  }
}

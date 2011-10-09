package de.johoop.testng;

import org.scalatools.testing.Event;
import org.testng.ITestResult;

public abstract class AbstractEvent implements Event {
  private final String testName;
  private final String description;
  private final Throwable error;

  AbstractEvent(final ITestResult result) {
    this.testName = result.getTestClass().getName() + "." + result.getName();
    this.description = "not available";
    this.error = result.getThrowable();
  }
  
  @Override public String testName() {
    return testName;
  }

  @Override public String description() {
    return description;
  }

  @Override public Throwable error() {
    return error;
  }
}

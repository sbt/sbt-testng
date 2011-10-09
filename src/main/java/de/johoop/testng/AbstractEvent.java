package de.johoop.testng;

import org.scalatools.testing.Event;
import org.scalatools.testing.Result;

public class AbstractEvent implements Event {

  private final String testName;
  private final String description;

  AbstractEvent(final String testName, final String description) {
    this.testName = testName;
    this.description = description;
  }
  
  @Override public String testName() {
    return testName;
  }

  @Override public String description() {
    return description;
  }

  @Override public Throwable error() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override public Result result() {
    // TODO Auto-generated method stub
    return null;
  }

}

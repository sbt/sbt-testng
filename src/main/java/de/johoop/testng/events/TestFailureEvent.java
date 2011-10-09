package de.johoop.testng.events;

import org.scalatools.testing.Result;
import org.testng.ITestResult;

public class TestFailureEvent extends AbstractEvent {

  public TestFailureEvent(ITestResult result) {
    super(result);
  }

  @Override public Result result() {
    return Result.Failure;
  }
}

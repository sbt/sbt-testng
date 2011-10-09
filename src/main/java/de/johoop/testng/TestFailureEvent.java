package de.johoop.testng;

import org.scalatools.testing.Result;
import org.testng.ITestResult;

public class TestFailureEvent extends AbstractEvent {

  TestFailureEvent(ITestResult result) {
    super(result);
  }

  @Override public Result result() {
    return Result.Failure;
  }
}

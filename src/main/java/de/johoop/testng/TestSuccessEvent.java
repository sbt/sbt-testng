package de.johoop.testng;

import org.scalatools.testing.Result;
import org.testng.ITestResult;

public class TestSuccessEvent extends AbstractEvent {

  TestSuccessEvent(ITestResult result) {
    super(result);
  }

  @Override public Result result() {
    return Result.Success;
  }
}

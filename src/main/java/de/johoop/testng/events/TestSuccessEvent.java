package de.johoop.testng.events;

import org.scalatools.testing.Result;
import org.testng.ITestResult;

public class TestSuccessEvent extends AbstractEvent {

  public TestSuccessEvent(ITestResult result) {
    super(result);
  }

  @Override public Result result() {
    return Result.Success;
  }
}

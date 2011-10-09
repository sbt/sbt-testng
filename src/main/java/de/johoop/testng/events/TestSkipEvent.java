package de.johoop.testng.events;

import org.scalatools.testing.Result;
import org.testng.ITestResult;

public class TestSkipEvent extends AbstractEvent {

  public TestSkipEvent(ITestResult result) {
    super(result);
  }

  @Override public Result result() {
    return Result.Skipped;
  }
}

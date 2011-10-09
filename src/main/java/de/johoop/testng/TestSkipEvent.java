package de.johoop.testng;

import org.scalatools.testing.Result;
import org.testng.ITestResult;

public class TestSkipEvent extends AbstractEvent {

  TestSkipEvent(ITestResult result) {
    super(result);
  }

  @Override public Result result() {
    return Result.Skipped;
  }
}

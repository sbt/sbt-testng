package de.johoop.testng;

import org.scalatools.testing.EventHandler;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class EventDispatcher extends TestListenerAdapter {

  private final EventHandler eventHandler;

  EventDispatcher(final EventHandler eventHandler) {
    this.eventHandler = eventHandler;
  }
  
  @Override
  public void onTestFailure(final ITestResult result) {
    eventHandler.handle(new TestFailureEvent(result));
    super.onTestFailure(result);
  }

  @Override
  public void onTestSkipped(final ITestResult result) {
    eventHandler.handle(new TestSkipEvent(result));
    super.onTestSkipped(result);
  }

  @Override
  public void onTestSuccess(final ITestResult result) {
    eventHandler.handle(new TestSuccessEvent(result));
    super.onTestSuccess(result);
  }

}

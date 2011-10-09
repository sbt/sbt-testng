package de.johoop.testng;

import org.scalatools.testing.EventHandler;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class EventDispatcher extends TestListenerAdapter {

  private final EventHandler eventHandler;
  private final TestNGLogger logger;

  EventDispatcher(final EventHandler eventHandler, final TestNGLogger logger) {
    this.eventHandler = eventHandler;
    this.logger = logger;
  }
  
  @Override
  public void onTestFailure(final ITestResult result) {
    super.onTestFailure(result);
  }

  @Override
  public void onTestSkipped(final ITestResult result) {
    super.onTestSkipped(result);
  }

  @Override
  public void onTestSuccess(final ITestResult result) {
    System.out.println(result.toString());
    super.onTestSuccess(result);
  }

}

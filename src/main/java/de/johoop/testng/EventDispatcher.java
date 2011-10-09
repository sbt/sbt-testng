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
    System.out.println("testname: " + result.getTestName());
    System.out.println("name: " + result.getName());
    System.out.println("host: " + result.getHost());
    System.out.println("method name: " + result.getMethod().getMethodName());
    System.out.println("method desc: " + result.getMethod().getDescription());
    System.out.println("method id: " + result.getMethod().getId());
    System.out.println("class test name: " + result.getTestClass().getTestName());
    System.out.println("class name: " + result.getTestClass().getName());
    
    eventHandler.handle(new TestSuccessEvent(result));
    super.onTestSuccess(result);
  }

}

package de.johoop.testng;

import java.util.Arrays;

import org.testng.CommandLineArgs;
import org.testng.TestNG;

import com.beust.jcommander.JCommander;

public class TestNGConfigurer {
  private final ConfigurableTestNG testNG;
  private ClassLoader testClassLoader;
  
  // yikes, the TestNG method we need is protected...
  private final static class ConfigurableTestNG extends TestNG {
    @Override public void configure(CommandLineArgs args) {
      super.configure(args);
    }
  }
  
  TestNGConfigurer() {
    testNG = new ConfigurableTestNG();
  }
  
  TestNGConfigurer forClassLoader(final ClassLoader classLoader) {
    testClassLoader = classLoader;
    return this;
  }

  TestNG configure(final String[] testOptions) {
    configureFromTestOptions(testOptions);
    configureClassLoader();
    
    return testNG;
  }

  private void configureFromTestOptions(final String[] testOptions) {
    System.out.println("options: " + Arrays.toString(testOptions));
    
    final CommandLineArgs args = new CommandLineArgs();
    new JCommander(args, testOptions); // yikes again, args is an output parameter of the constructor!
    testNG.configure(args);
  }

  private void configureClassLoader() {
    if (testClassLoader != null) {
      testNG.addClassLoader(testClassLoader);
    }
  }
}

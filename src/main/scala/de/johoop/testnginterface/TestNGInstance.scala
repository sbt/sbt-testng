package de.johoop.testnginterface

import org.scalatools.testing.EventHandler

import org.testng.CommandLineArgs
import org.testng.TestNG

import com.beust.jcommander.JCommander

class TestNGInstance {
  def loadingClassesFrom(testClassLoader: ClassLoader): TestNGInstance = {
    ConfigurableTestNG addClassLoader testClassLoader
    TestNGInstance.this
  }
  
  def using(testOptions: Array[String]): TestNGInstance = {
    configureFrom(testOptions:_*)
    TestNGInstance.this
  }
  
  def forwardingEventsTo(eventHandler: EventHandler): TestNGInstance = {
    ConfigurableTestNG addListener (Forwarder to eventHandler)
    TestNGInstance.this
  }
  
  private def configureFrom(testOptions: String*) {
    val args = new CommandLineArgs()
    new JCommander(args, testOptions:_*) // args is an output parameter of the constructor!
    ConfigurableTestNG configure args
  }
  
  private object ConfigurableTestNG extends TestNG { // the TestNG method we need is protected
    override def configure(args: CommandLineArgs) = super.configure(args)
  }
}
object TestNGInstance {
  def start(testNG: TestNGInstance): Unit = testNG.ConfigurableTestNG.run 
}

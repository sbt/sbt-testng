package de.johoop.testnginterface

import org.scalatools.testing.EventHandler

import org.testng.CommandLineArgs
import org.testng.TestNG

import com.beust.jcommander.JCommander

class ConfiguredTestNG {
  def loadingClassesFrom(testClassLoader: ClassLoader): ConfiguredTestNG = {
    ConfigurableTestNG addClassLoader testClassLoader
    this
  }
  
  def using(testOptions: Array[String]): ConfiguredTestNG = {
    configureFrom(testOptions:_*)
    this
  }
  
  def forwardingEventsTo(eventHandler: EventHandler): ConfiguredTestNG = {
    ConfigurableTestNG addListener (Forwarder to eventHandler)
    this
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
object ConfiguredTestNG {
  def start(testNG: ConfiguredTestNG): Unit = testNG.ConfigurableTestNG.run 
}

package de.johoop.testng;

import org.scalatools.testing.Logger;

public class TestNGLogger {
  private final Logger[] loggers;

  TestNGLogger(final Logger[] loggers) {
    this.loggers = loggers;
  }
  
  void error(final String msg) {
    for (final Logger logger : loggers) logger.error(msg);
  }

  void warn(final String msg) {
    for (final Logger logger : loggers) logger.warn(msg);
  }

  void info(final String msg) {
    for (final Logger logger : loggers) logger.info(msg);
  }

  void debug(final String msg) {
    for (final Logger logger : loggers) logger.debug(msg);
  }
}

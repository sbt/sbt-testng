package de.johoop.testng;

import org.scalatools.testing.AnnotatedFingerprint;

public class TestNGFingerprint implements AnnotatedFingerprint {

  @Override public String annotationName() {
    return "org.testng.annotations.Test";
  }

  @Override public boolean isModule() {
    return false;
  }
}

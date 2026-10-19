package ru.etu.web;

import ru.etu.web.client.TemplateTest;
import com.google.gwt.junit.tools.GWTTestSuite;
import junit.framework.Test;
import junit.framework.TestSuite;

public class TemplateSuite extends GWTTestSuite {
  public static Test suite() {
    TestSuite suite = new TestSuite("Tests for Template");
    suite.addTestSuite(TemplateTest.class);
    return suite;
  }
}

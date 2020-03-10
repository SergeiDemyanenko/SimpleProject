package com.selenium;

import junit.framework.TestCase;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;

public class CoreTestCase extends TestCase {

    protected WebDriver driver;

    Platform platform;

    public CoreTestCase() {
        super.setUp();
        this.platform = new Platform();
        this.driver 
    }
}

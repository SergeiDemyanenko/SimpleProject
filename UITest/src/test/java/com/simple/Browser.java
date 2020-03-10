package com.simple;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.wdm.WebDriverManager;


class Platform {

    private static final String BROWSER_CHROME = "chrome";
    private static final String BROWSER_FIREFOX = "firefox";

    private static Platform instance;

    private Platform() {}

    public static Platform getInstance() {
        if (instance==null) {
            instance = new Platform();
        }
        return instance;
    }


    private String getBrowserVar() {
        return System.getenv("BROWSER");
    }

    private boolean isBrowser(String my_browser) {
        String browser = this.getBrowserVar();
        return my_browser.equals(browser);

    }

    private boolean isChrome() {

        return this.isBrowser(BROWSER_CHROME);

    }

    private boolean isFirefox() {

        return this.isBrowser(BROWSER_FIREFOX);

    }


    public WebDriver getDriver() throws Exception {

        WebDriver driver;

        if (this.isChrome()) {
        	WebDriverManager.chromedriver().setup();
        	driver = new ChromeDriver();
		} else if (this.isFirefox()) {
        	WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
		} else throw new Exception("Cannot find browser " + this.getBrowserVar());

        return driver;

    }


}

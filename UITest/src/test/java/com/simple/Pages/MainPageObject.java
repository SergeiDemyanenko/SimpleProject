package com.simple;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainPageObject {


    protected WebDriver driver;


    public MainPageObject(WebDriver driver) {
        this.driver = driver;
    }

    WebElement waitForElementPresent(By by, String errorMessage, long timeOutInSec) {

        WebDriverWait wait = new WebDriverWait(driver, timeOutInSec);

        wait.withMessage(errorMessage);

        return wait.until(ExpectedConditions.presenceOfElementLocated(by));

    }


    //   public We


}

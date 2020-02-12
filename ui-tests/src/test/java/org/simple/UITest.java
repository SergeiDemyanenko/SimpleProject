package org.simple;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfAllElementsLocatedBy;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class UITest {

    @Test
    public void addTest() {
        System.setProperty("webdriver.chrome.driver", "/Users/sdemyanenko/Projects/chromedriver");

        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, 5);
        try {
            final String TEST_VALUE = "test string";

            driver.get("http://localhost:3000/");

            WebElement input = wait.until(presenceOfElementLocated(By.className("new-todo-label")));
            input.sendKeys(TEST_VALUE + Keys.ENTER);

            List<WebElement> labelList = wait.until(presenceOfAllElementsLocatedBy(By.className("todo-list-item-label")));
            assertTrue("added string not found", labelList.stream().anyMatch(e -> TEST_VALUE.equals(e.getText())));
        } finally {
            driver.close();
        }
    }
}

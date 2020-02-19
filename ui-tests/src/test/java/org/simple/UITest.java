package org.simple;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfAllElementsLocatedBy;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class UITest {

    @Test
    public void addTest() {
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

    @Test
    public void deleteTest() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, 5);
        try {
            driver.get("http://localhost:3000/");

            By deleteButton = By.xpath("//i[@class='fa fa-trash-o']");

            List<WebElement> todoList = wait.until(presenceOfAllElementsLocatedBy(By.xpath("//span[@class='todo-list-item']")));
            assertTrue("There are nothing to delete in the List",todoList.size() > 0);

            todoList.get(todoList.size() - 1).findElement(deleteButton).click();
            List<WebElement> newTodoList = wait.until(presenceOfAllElementsLocatedBy(By.xpath("//span[@class='todo-list-item']")));

            assertEquals(todoList.size() - 1, newTodoList.size());
        } finally {
            driver.close();
        }
    }
}

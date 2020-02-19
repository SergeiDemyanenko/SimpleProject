package org.simple;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

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
    public void deleteTest(){
        WebDriver driver = new ChromeDriver();
        MainPage mainPage = new MainPage(driver);
        try {
            driver.get("http://localhost:3000/");

            int todoListSize = mainPage.getAllTodoItems().size();
            assertTrue("There are nothing to delete in the List", todoListSize > 0);

            mainPage.deleteFirstTodo();
            assertEquals(todoListSize - 1, mainPage.getAllTodoItems().size());
        } finally {
            driver.close();
        }
    }
}

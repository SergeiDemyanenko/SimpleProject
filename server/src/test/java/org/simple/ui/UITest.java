package org.simple.ui;

import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.simple.ui.pages.MainPage;
import org.simple.utils.RunServer;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfAllElementsLocatedBy;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import static org.springframework.test.util.AssertionErrors.assertNotNull;


@RunServer
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UITest {

    private static String HUB_URL = "http://localhost:4444/wd/hub";
    private static boolean remoteWebDriver = false;

    @BeforeAll
    private static void setUpAll() throws IOException {

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(HUB_URL + "/status");
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                remoteWebDriver = response.getStatusLine().getStatusCode() == HttpStatus.SC_OK;
            } catch (HttpHostConnectException e) {}
        }
    }

    @LocalServerPort
    private int randomServerPort;

    private WebDriver driver;
    private WebDriverWait driverWait;

    private void setUp() throws MalformedURLException {

        if (remoteWebDriver) {
            driver = new RemoteWebDriver(new URL(HUB_URL), DesiredCapabilities.chrome());
        } else {
            driver = new ChromeDriver();
        }

        driverWait = new WebDriverWait(driver, 5);
    }


    protected WebDriver getDriver() {
        return driver;
    }

    protected WebDriverWait getDriverWait() {
        return driverWait;
    }

    protected String getHost() {
        if (remoteWebDriver) {
            return String.format("http://localhost.host:%d/", randomServerPort);
        } else {
            return String.format("http://localhost:%d/", randomServerPort);
        }
    }

    @AfterEach
    private void setDown() {
        driver.quit();
    }

    @BeforeEach
    public void signUp() throws MalformedURLException {

        setUp();
        final String TEST_LOGIN = "TEST_LOGIN" + Math.random();
        final String TEST_PASSWORD = "TEST_PASSWORD" ;

        getDriver().get(getHost() + "signUp");

        WebElement signUpButton = getDriverWait().until(presenceOfElementLocated((By.xpath("//button[@type='button']"))));
        WebElement inputLogin = driver.findElement(By.xpath("//input[@type='text']"));
        WebElement inputPassword = driver.findElement(By.xpath("//input[@type='password']"));

        inputLogin.sendKeys(TEST_LOGIN);
        inputPassword.sendKeys(TEST_PASSWORD);
        signUpButton.click();

        getDriverWait().until(presenceOfElementLocated(By.xpath("//input[@placeholder='What needs to be done?']")));

        assertEquals(getHost(), driver.getCurrentUrl());
    }

    @Test
    @Order(1)
    public void addTest() {
        final String TEST_VALUE = "test string";

        WebElement input = getDriverWait().until(presenceOfElementLocated(By.className("new-todo-label")));
        List<WebElement> labelList = getDriverWait().until(presenceOfAllElementsLocatedBy(By.className("todo-list-item-label")));

        input.sendKeys(TEST_VALUE + Keys.ENTER);

        assertNotNull("added string not found",
                getDriverWait().until(presenceOfElementLocated(By.xpath(String.format("(//span[@class=\"todo-list-item-label\"])[%d]", labelList.size() + 1)))));
    }

    @Test
    @Order(2)
    public void deleteTest() throws InterruptedException {
        MainPage mainPage = new MainPage(getDriver());

        int todoListSize = mainPage.getAllTodoItems().size();
        Assert.assertTrue("There are nothing to delete in the List", todoListSize > 0);

        mainPage.deleteLastTodo();
        Thread.sleep(300);

        assertEquals(todoListSize - 1, mainPage.getAllTodoItems().size());
    }

    @Disabled
    @Test
    public void editTest() throws InterruptedException {
        MainPage mainPage = new MainPage(driver);

        driver.get("http://localhost:3000/");

        List<WebElement> todoList = mainPage.getAllTodoItems();
        Assert.assertTrue("There are nothing to edit in the List", todoList.size() > 0);

        mainPage.editFirstTodo(todoList);

        assertEquals(mainPage.getFirstElement().getText(), mainPage.EDIT_VALUE);
    }
}

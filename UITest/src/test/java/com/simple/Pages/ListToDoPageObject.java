package com.simple;

import org.openqa.selenium.WebDriver;

public class ListToDoPageObject1 extends MainPageObject {

    private static final String URL = "http://127.0.0.1:8080";
    private static final String title = "Todo List";



    public ListToDoPageObject1(WebDriver driver) throws Exception {
        super(driver);
    }

    public void open() {

        driver.get(URL);
    }


}

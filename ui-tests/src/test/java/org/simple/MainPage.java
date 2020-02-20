package org.simple;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class MainPage extends PageObject {

    public MainPage(WebDriver driver) {
        super(driver);
    }

    static By todoItemSelector = By.xpath("//span[@class='todo-list-item']");
    static By deleteButtonSelector = By.xpath("//button[@type='button']/i[@class='fa fa-trash-o']");

    @FindBy(xpath = "//body")
    private WebElement pageBody;

    public List<WebElement> getAllTodoItems(){
        List<WebElement> todoList = pageBody.findElements(todoItemSelector);
        return todoList;
    }

    public void deleteFirstTodo(){
        getAllTodoItems().get(0).findElement(deleteButtonSelector).click();
    }
}

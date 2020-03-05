package org.simple.ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.simple.ui.PageObject;

import java.util.List;

public class MainPage extends PageObject {

    public MainPage(WebDriver driver) {
        super(driver);
    }

    public final String EDIT_VALUE = "EDITED-VALUE";

    static By todoItemSelector = By.xpath("//span[@class='todo-list-item']");
    static By deleteButtonSelector = By.xpath("//button[@type='button']/i[@class='fa fa-trash-o']");
    static By editFirstButtonSelector = By.xpath("//button[@type='button']/i[@class='fa fa-edit']");
    static By editFinalButtonSelector = By.xpath("//form/button[@type='submit']");
    static By editFieldSelector = By.xpath("//form[@class='center-panel d-flex']/input[@type='text']");

    @FindBy(xpath = "//body")
    private WebElement pageBody;

    public List<WebElement> getAllTodoItems(){
        List<WebElement> todoList = pageBody.findElements(todoItemSelector);
        return todoList;
    }

    public WebElement getFirstElement(){
        return getAllTodoItems().get(0);
    }

    public void deleteFirstTodo(){
        getAllTodoItems().get(0).findElement(deleteButtonSelector).click();
    }

    public void editFirstTodo(List<WebElement> todoList){
        todoList.get(0).findElement(editFirstButtonSelector).click();
        WebElement newFirstElement = getFirstElement();
        newFirstElement.findElement(editFieldSelector).clear();
        newFirstElement.findElement(editFieldSelector).sendKeys(EDIT_VALUE);
        newFirstElement.findElement(editFinalButtonSelector).click();
    }
}

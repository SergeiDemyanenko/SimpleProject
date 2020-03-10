package com.simple;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;

public class NewTest extends CoreTestCase {

    private ListToDoPageObject listToDoPageObject;



    @Test
    public void testic() throws Exception {

        listToDoPageObject = new ListToDoPageObject(driver);


        System.out.println("");
        listToDoPageObject.open();
        Thread.sleep(5000);

    }


}

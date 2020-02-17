package org.simple;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTests {

    @LocalServerPort
    private int randomServerPort;

    private CloseableHttpClient httpClient = HttpClients.createDefault();

    @Test
    public void helloTest() throws IOException {
        HttpGet request = new HttpGet(String.format("http://localhost:%d/api/hello", randomServerPort));
        try (CloseableHttpResponse response = httpClient.execute(request)) {

            assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());

            HttpEntity entity = response.getEntity();
            assertNotNull("response is null", entity);
            assertEquals("hello", EntityUtils.toString(entity));
        }
    }

    @Test
    public void addToDBTest() throws SQLException, IOException {
        final String TEST_VALUE = "TestString";
        HttpGet request = new HttpGet(String.format("http://localhost:%d/api/add?newToDo=%s", randomServerPort, TEST_VALUE));
        try (CloseableHttpResponse response = httpClient.execute(request)) {


            assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());

            ArrayList<String> todoList = new ArrayList<>();
            Connection conn = DataBaseUtils.getConnect();
            Statement sql_stmt = conn.createStatement();
            ResultSet rset = sql_stmt.executeQuery("SELECT id, text FROM TODO_LIST WHERE text = 'TestString'");
            while (rset.next()) {
                todoList.add(rset.getString("text"));
            }
            assertEquals(todoList.size(), 1);
            assertEquals(TEST_VALUE, todoList.get(todoList.size() - 1));
        }
    }
}
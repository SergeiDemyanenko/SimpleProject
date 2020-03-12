package org.simple.integration;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.*;
import org.simple.utils.RunServer;
import org.simple.DataBaseUtils;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.IOException;
import java.sql.*;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.util.AssertionErrors.*;

@RunServer
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ApiTest {

    private static final String SQL_GET_TODO_LIST_BY_ID = "SELECT id, text FROM TODO_LIST WHERE id = ?";

    private static int testId;

    @LocalServerPort
    private int randomServerPort;

    private String getResponse(String uri) throws IOException {
        String result;

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(String.format("http://localhost:%d%s", randomServerPort, uri));
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());

                HttpEntity entity = response.getEntity();
                assertNotNull("response is null", entity);
                result = EntityUtils.toString(entity);
            }
        }

        return result;
    }

    private ResultSet getResultSet(String sql, Object... params) throws SQLException {
        Connection conn = DataBaseUtils.getConnect();
        PreparedStatement stmt = conn.prepareStatement(sql);

        for (int i = 0; i < params.length; i++) {
            stmt.setObject(i + 1, params[i]);
        }

        return stmt.executeQuery();
    }

    @Test
    @Order(1)
    public void helloTest() throws IOException {
        assertEquals("hello", getResponse("/api/hello"));
    }

    @Test
    @Order(2)
    public void listTest() throws IOException {
        assertEquals("[{\"id\":1,\"text\":\"go to settings\"},{\"id\":2,\"text\":\"go to online store\"}," +
                "{\"id\":3,\"text\":\"delete unwanted items from your shopping cart\"}," +
                "{\"id\":4,\"text\":\"change your home address\"}," +
                "{\"id\":5,\"text\":\"save changes\"}," +
                "{\"id\":6,\"text\":\"buy some goods\"}," +
                "{\"id\":7,\"text\":\"go to shopping cart\"}," +
                "{\"id\":8,\"text\":\"check your shopping cart\"}," +
                "{\"id\":9,\"text\":\"finish shopping\"}]", getResponse("/api/list-obj"));
    }

    @Test
    @Order(3)
    public void addTest() throws SQLException, IOException {
        final String TEST_VALUE = getClass().getName() + "_addTest";

        testId = Integer.parseInt(getResponse(String.format("/api/add?text=%s", TEST_VALUE)));
        ResultSet resultSet = getResultSet(SQL_GET_TODO_LIST_BY_ID, testId);

        assertTrue(String.format("There is no record with id = %d in SQL database", testId), resultSet.next());
        assertEquals(TEST_VALUE, resultSet.getString(2));
        assertFalse(String.format("There is more then one record with id = %d in SQL database", testId), resultSet.next());
    }

    @Test
    @Order(4)
    public void editTest() throws IOException, SQLException {
        final String TEST_VALUE = getClass().getName() + "_editTest";

        getResponse(String.format("/api/edit?id=%d&text=%s", testId, TEST_VALUE));
        ResultSet resultSet = getResultSet(SQL_GET_TODO_LIST_BY_ID, testId);

        assertTrue(String.format("There is no records with id = %d in SQL database", testId), resultSet.next());
        assertEquals(TEST_VALUE, resultSet.getString(2));
        assertFalse(String.format("There is more then one record with id = %d in SQL database", testId), resultSet.next());
    }

    @Test
    @Order(5)
    public void deleteTest() throws IOException, SQLException {
        getResponse(String.format("/api/delete?id=%d", testId));
        ResultSet resultSet = getResultSet(SQL_GET_TODO_LIST_BY_ID, testId);

        assertFalse(String.format("There is no record with id = %d in SQL database", testId), resultSet.next());
    }

    @Test
    @Order(6)
    public void listGroupTest() throws IOException, SQLException {
        assertEquals(
                "[{\"group_id\":1,\"group_name\":\"Group 1\",\"todoFormedGroup\":[{\"id\":2,\"text\":\"" +
                        "go to online store\"},{\"id\":3,\"text\":\"delete unwanted items from your shopping cart\"}," +
                        "{\"id\":6,\"text\":\"buy some goods\"},{\"id\":8,\"text\":\"check your shopping cart\"}]}," +
                        "{\"group_id\":2,\"group_name\":\"Group 2\",\"todoFormedGroup\":[{\"id\":1,\"text\":\"" +
                        "go to settings\"},{\"id\":4,\"text\":\"change your home address\"},{\"id\":5,\"text\":\"save changes\"}]}," +
                        "{\"group_id\":3,\"group_name\":\"Group 3\",\"todoFormedGroup\":[{\"id\":7,\"text\":\"" +
                        "go to shopping cart\"},{\"id\":9,\"text\":\"finish shopping\"}]}]",
                getResponse("/api/list-group"));
    }
}
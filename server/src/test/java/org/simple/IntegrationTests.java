package org.simple;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.sql.*;
import java.sql.SQLException;


import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTests {

    @LocalServerPort
    private int randomServerPort;

    private CloseableHttpClient httpClient = HttpClients.createDefault();

    private String getResponse(String uri) throws IOException {
        String result;

        HttpGet request = new HttpGet(String.format("http://localhost:%d%s", randomServerPort, uri));
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());

            HttpEntity entity = response.getEntity();
            assertNotNull("response is null", entity);
            result = EntityUtils.toString(entity);
        }

        return result;
    }

    @Test
    @Order(1)
    public void helloTest() throws IOException {
        assertEquals("hello", getResponse("/api/hello"));
    }

    @Test
    @Order(2)
    public void listTest() throws IOException {
        assertEquals("[{\"id\":1,\"text\":\"go to\"},{\"id\":2,\"text\":\"do something\"}," +
                "{\"id\":3,\"text\":\"look at it\"}]", getResponse("/api/list-obj"));
    }

    @Test
    @Order(3)
    public void deleteTest() throws IOException, SQLException {
        DataBaseUtils.deleteRecord(2);
        assertEquals("[{\"id\":1,\"text\":\"go to\"}," +
                "{\"id\":3,\"text\":\"look at it\"}]", getResponse("/api/list-obj"));
    }

    @Test
    @Order(4)
    public void addToDBTest() throws SQLException, IOException {
        final String TEST_VALUE = "Test_Add_To_Db_Value";
        getResponse(String.format("/api/add?text=%s", TEST_VALUE));

        Connection conn = DataBaseUtils.getConnect();
        PreparedStatement stmt = conn.prepareStatement("SELECT text FROM TODO_LIST WHERE text = ?");
        stmt.setString(1, TEST_VALUE);
        ResultSet rset = stmt.executeQuery();

        assertTrue("There is not records in SQL", rset.next());
        assertEquals(TEST_VALUE, rset.getString(1));
        assertFalse(String.format("There is more then one record with text = %s,", TEST_VALUE), rset.next());
    }
}
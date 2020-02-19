package org.simple;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.sql.*;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.util.AssertionErrors.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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

        assertTrue(String.format("There is not records with text = %s in SQL database", TEST_VALUE) , rset.next());
        assertEquals(TEST_VALUE, rset.getString(1));
        assertFalse(String.format("There is more then one record with text = %s in SQL database", TEST_VALUE), rset.next());
    }

    @Test
    @Order(5)
    public void editTest() throws IOException, SQLException {
        DataBaseUtils.editRecord(3,"do not look at it");
        assertEquals("[{\"id\":1,\"text\":\"go to\"},{\"id\":3,\"text\":\"do not look at it\"}," +
                "{\"id\":4,\"text\":\"Test_Add_To_Db_Value\"}]", getResponse("/api/list-obj"));
    }
}
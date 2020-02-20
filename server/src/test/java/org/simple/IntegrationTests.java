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
        int TEST_ID = 0;
        String NEW_VALUE = "do_not_look_at_it";

        Connection conn0 = DataBaseUtils.getConnect();
        PreparedStatement stmt0 = conn0.prepareStatement("SELECT MAX(id) FROM TODO_LIST");
        ResultSet rset0 = stmt0.executeQuery();
        if (rset0.next()) {
            TEST_ID = rset0.getInt(1);
        }

        getResponse(String.format("/api/edit?id=%d&text=%s", TEST_ID, NEW_VALUE));

        PreparedStatement stmt1 = conn0.prepareStatement("SELECT text FROM TODO_LIST WHERE text = ?");
        stmt1.setString(1, NEW_VALUE);
        ResultSet rset1 = stmt1.executeQuery();

        assertTrue(String.format("There is not records with text = %s in SQL database", NEW_VALUE) , rset1.next());
        assertEquals(NEW_VALUE, rset1.getString(1));
        assertFalse(String.format("There is more then one record with text = %s in SQL database", NEW_VALUE), rset1.next());
    }
}
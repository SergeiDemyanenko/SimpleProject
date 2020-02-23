package org.simple;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.*;
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

    private static int testId;

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
        assertEquals("[{\"id\":1,\"text\":\"go to settings\",\"group_id\":2}," +
                "{\"id\":2,\"text\":\"go to online store\",\"group_id\":1}," +
                "{\"id\":3,\"text\":\"delete unwanted items from your shopping cart\",\"group_id\":1}," +
                "{\"id\":4,\"text\":\"change your home address\",\"group_id\":2}," +
                "{\"id\":5,\"text\":\"save changes\",\"group_id\":2}," +
                "{\"id\":6,\"text\":\"buy some goods\",\"group_id\":1}," +
                "{\"id\":7,\"text\":\"go to shopping cart\",\"group_id\":3}," +
                "{\"id\":8,\"text\":\"check your shopping cart\",\"group_id\":1}," +
                "{\"id\":9,\"text\":\"finish shopping\",\"group_id\":3}]", getResponse("/api/list-obj"));
    }

    @Test
    @Order(3)
    public void listGroupTest() throws IOException {
        assertEquals("[{\"id\":2,\"text\":\"go to online store\",\"group_id\":1}," +
                "{\"id\":3,\"text\":\"delete unwanted items from your shopping cart\",\"group_id\":1}," +
                "{\"id\":8,\"text\":\"check your shopping cart\",\"group_id\":1}," +
                "{\"id\":6,\"text\":\"buy some goods\",\"group_id\":1}," +
                "{\"id\":1,\"text\":\"go to settings\",\"group_id\":2}," +
                "{\"id\":5,\"text\":\"save changes\",\"group_id\":2}," +
                "{\"id\":4,\"text\":\"change your home address\",\"group_id\":2}," +
                "{\"id\":9,\"text\":\"finish shopping\",\"group_id\":3}," +
                "{\"id\":7,\"text\":\"go to shopping cart\",\"group_id\":3}]", getResponse("/api/list-obj-group"));
    }

    @Test
    @Order(4)
    public void addToDBTest() throws SQLException, IOException {
        String TEST_VALUE = "Test_Add_To_Db_Value";

        testId = Integer.parseInt(getResponse(String.format("/api/add?text=%s", TEST_VALUE)));

        Connection conn = DataBaseUtils.getConnect();
        PreparedStatement stmt = conn.prepareStatement("SELECT id, text FROM TODO_LIST WHERE id = ?");
        stmt.setInt(1, testId);
        ResultSet rset = stmt.executeQuery();

        assertTrue(String.format("There is no record with id = %d in SQL database", testId), rset.next());
        assertEquals(TEST_VALUE, rset.getString(2));
        assertFalse(String.format("There is more then one record with id = %d in SQL database", testId), rset.next());
    }

    @Test
    @Order(5)
    public void editTest() throws IOException, SQLException {
        String NEW_VALUE = "do_not_look_at_it";

        getResponse(String.format("/api/edit?id=%d&text=%s", testId, NEW_VALUE));

        Connection conn = DataBaseUtils.getConnect();
        PreparedStatement stmt = conn.prepareStatement("SELECT id, text FROM TODO_LIST WHERE id = ?");
        stmt.setInt(1, testId);
        ResultSet rset = stmt.executeQuery();

        assertTrue(String.format("There is no records with id = %d in SQL database", testId), rset.next());
        assertEquals(NEW_VALUE, rset.getString(2));
        assertFalse(String.format("There is more then one record with id = %d in SQL database", testId), rset.next());
    }

    @Test
    @Order(6)
    public void deleteTest() throws IOException, SQLException {
        getResponse(String.format("/api/delete?id=%d", testId));

        Connection conn = DataBaseUtils.getConnect();
        PreparedStatement stmt = conn.prepareStatement("SELECT id FROM TODO_LIST WHERE id = ?");
        stmt.setInt(1, testId);
        ResultSet rset = stmt.executeQuery();

        assertFalse(String.format("There is no record with id = %d in SQL database", testId), rset.next());
    }
}
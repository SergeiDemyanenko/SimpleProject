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
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertNotNull;

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
}
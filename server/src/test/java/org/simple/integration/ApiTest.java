package org.simple.integration;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.hamcrest.Matchers;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.simple.entity.ToDoGroup;
import org.simple.entity.ToDoGroupRepository;
import org.simple.entity.ToDoItem;
import org.simple.entity.ToDoItemRepository;
import org.simple.utils.RunServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertNotNull;
import static org.springframework.test.util.AssertionErrors.assertNull;

@RunServer
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ApiTest {

    @Autowired
    private ToDoItemRepository toDoItemRepository;

    @Autowired
    private ToDoGroupRepository toDoGroupRepository;

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
    @Before
    public void setup() {
        RestAssured.baseURI = "http://localhost:";
        RestAssured.port = randomServerPort;
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
    public void addTest() throws JSONException {
        final String TEST_VALUE = getClass().getName() + "_addTest";

        JSONObject requestParams = new JSONObject();
        requestParams.put("text", TEST_VALUE);

        RestAssured.given()
                .port(randomServerPort)
                .contentType(ContentType.JSON)
                .body(requestParams.toString())
                .when()
                .post("/api/add")
                .then()
                .body("text", Matchers.equalTo(TEST_VALUE));
    }

    @Test
    @Order(4)
    public void deleteTest() throws JSONException {

        JSONObject requestParams = new JSONObject();
        requestParams.put("id", testId);

        RestAssured.given()
                .port(randomServerPort)
                .delete(String.format("api/delete?id=%d", testId));

        ToDoItem deletedItem = toDoItemRepository.findById(testId);

        assertNull("TodoItem wasn`t delete", deletedItem);
    }

    @Test
    @Order(5)
    public void listGroupTest() throws IOException {
        assertEquals(
                "[{\"id\":1,\"group_name\":\"Group 1\",\"toDoItems\":" +
                        "[{\"id\":2,\"text\":\"go to online store\"}," +
                        "{\"id\":3,\"text\":\"delete unwanted items from your shopping cart\"}," +
                        "{\"id\":6,\"text\":\"buy some goods\"}," +
                        "{\"id\":8,\"text\":\"check your shopping cart\"}]}," +
                        "{\"id\":2,\"group_name\":\"Group 2\",\"toDoItems\":" +
                        "[{\"id\":1,\"text\":\"go to settings\"}," +
                        "{\"id\":4,\"text\":\"change your home address\"}," +
                        "{\"id\":5,\"text\":\"save changes\"}]}," +
                        "{\"id\":3,\"group_name\":\"Group 3\",\"toDoItems\":" +
                        "[{\"id\":7,\"text\":\"go to shopping cart\"}," +
                        "{\"id\":9,\"text\":\"finish shopping\"}]}]",
                getResponse("/api/list-group"));
    }

    @Test
    @Order(6)
    public void editTest() throws IOException, JSONException {
        final String TEST_VALUE = getClass().getName() + "_editTest";

        String testToDoGroupsString = getResponse("/api/list-group");
        JSONArray testToDoGroupsJSON = new JSONArray(testToDoGroupsString);
        JSONObject firstItem = testToDoGroupsJSON.getJSONObject(0).getJSONArray("toDoItems").getJSONObject(0);
        testId = firstItem.getInt("id");

        JSONObject requestParams = new JSONObject();
        requestParams.put("id", testId);
        requestParams.put("text", TEST_VALUE);

        RestAssured.given()
                .port(randomServerPort)
                .contentType(ContentType.JSON)
                .body(requestParams.toString())
                .when()
                .patch("api/edit");

        ToDoItem editedToDoItem = toDoItemRepository.findById(testId);

        assertEquals(TEST_VALUE, editedToDoItem.getText());
    }

    @Test
    @Order(7)
    public void addGroupTest() throws JSONException {
        final String TEST_VALUE = getClass().getName() + "_addGroupTest";

        JSONObject requestParams = new JSONObject();
        requestParams.put("group_name", TEST_VALUE);

        RestAssured.given()
                .port(randomServerPort)
                .contentType(ContentType.JSON)
                .body(requestParams.toString())
                .when().post("api/addGroup")
                .then()
                .statusCode(200)
                .body("group_name", Matchers.equalTo(TEST_VALUE))
                .body("id", Matchers.notNullValue())
                .body("toDoItems", Matchers.nullValue());
    }

    @Test
    @Order(8)
    public void editGroupTest() throws IOException, JSONException {
        final String TEST_VALUE = getClass().getName() + "_editGroupTest";

        String testToDoGroupsString = getResponse("/api/list-group");
        JSONArray testToDoGroupsJSON = new JSONArray(testToDoGroupsString);
        JSONObject firstItem = testToDoGroupsJSON.getJSONObject(0);
        testId = firstItem.getInt("id");

        JSONObject requestParams = new JSONObject();
        requestParams.put("id", testId);
        requestParams.put("group_name", TEST_VALUE);

        RestAssured.given()
                .port(randomServerPort)
                .contentType(ContentType.JSON)
                .body(requestParams.toString())
                .when().patch("api/editGroup")
                .then()
                .statusCode(200);

        ToDoGroup editedToDoGroup = toDoGroupRepository.findById(testId);

        assertEquals(editedToDoGroup.getId(), testId);
        assertEquals(editedToDoGroup.getGroup_name(), TEST_VALUE);
    }

    @Test
    @Order(9)
    public void deleteGroupTest() throws JSONException, IOException {

        String testToDoGroupsString = getResponse("/api/list-group");
        JSONArray testToDoGroupsJSON = new JSONArray(testToDoGroupsString);
        JSONObject firstItem = testToDoGroupsJSON.getJSONObject(0);
        testId = firstItem.getInt("id");

        RestAssured.given()
                .port(randomServerPort)
                .when().delete(String.format("api/deleteGroup?id=%d", testId))
                .then()
                .statusCode(200);

        ToDoGroup deletedGroup = toDoGroupRepository.findById(testId);

        assertNull("TodoGroup wasn`t delete", deletedGroup);
    }
}
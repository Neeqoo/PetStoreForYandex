package api.clients;

import api.models.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserClient {
    private static final String BASE_URL = "https://petstore.swagger.io/v2";
    private static final String USER_PATH = "/user";
    private static final String USER_NAME = "/{username}";

    public Response createUser(User user) {
        return given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post(USER_PATH);
    }

    public Response getUser(String username) {
        return given()
                .baseUri(BASE_URL)
                .pathParam("username", username)
                .when()
                .get(USER_PATH + USER_NAME);
    }

    public Response updateUser(String username, User user) {
        return given()
                .baseUri(BASE_URL)
                .pathParam("username", username)
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .put(USER_PATH + USER_NAME);
    }

    public Response deleteUser(String username) {
        return given()
                .baseUri(BASE_URL)
                .pathParam("username", username)
                .when()
                .delete(USER_PATH + USER_NAME);
    }
}
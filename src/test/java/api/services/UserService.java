package api.services;

import api.models.User;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class UserService {
    private static final String BASE_URL = "https://petstore.swagger.io/v2";
    private static final String USER_PATH = "/user";

    private RequestSpecification given() {
        return RestAssured.given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON);
    }

    public Response createUser(User user) {
        return given()
                .body(user)
                .when()
                .post(USER_PATH);
    }


    public Response getUser(String username) {
        return given()
                .when()
                .get(USER_PATH + "/" + username);
    }

    public Response updateUser(String username, User user) {
        return given()
                .body(user)
                .when()
                .put(USER_PATH + "/" + username);
    }

    public Response deleteUser(String username) {
        return given()
                .when()
                .delete(USER_PATH + "/" + username);
    }
}
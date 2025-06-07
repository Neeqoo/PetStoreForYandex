package api.services;

import api.models.Pet;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class PetService {
    private static final String BASE_URL = "https://petstore.swagger.io/v2";
    private static final String PET_PATH = "/pet";
    private static final String PET_BY_ID_PATH = PET_PATH + "/{petId}";

    private RequestSpecification given() {
        return RestAssured.given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON);
    }

    public Response createPet(Pet pet) {
        return given()
                .body(pet)
                .when()
                .post(PET_PATH);
    }

    public Response getPetById(long petId) {
        return given()
                .pathParam("petId", petId)
                .when()
                .get(PET_BY_ID_PATH);
    }

    public Response updatePet(Pet pet) {
        return given()
                .body(pet)
                .when()
                .put(PET_PATH);
    }

    public Response deletePet(long petId) {
        return given()
                .pathParam("petId", petId)
                .when()
                .delete(PET_BY_ID_PATH);
    }
}
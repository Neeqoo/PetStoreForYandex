package api.clients;

import api.models.Pet;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class PetClient {
    private static final String BASE_URL = "https://petstore.swagger.io/v2";
    private static final String PET_PATH = "/pet";
    private static final String PET_ID = "/{petId}";

    public Response createPet(Pet pet) {
        return given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .body(pet)
                .when()
                .post(PET_PATH);
    }

    public Response getPetById(long petId) {
        return given()
                .baseUri(BASE_URL)
                .pathParam("petId", petId)
                .when()
                .get(PET_PATH + PET_ID);
    }

    public Response updatePet(Pet pet) {
        return given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .body(pet)
                .when()
                .put(PET_PATH);
    }

    public Response deletePet(long petId) {
        return given()
                .baseUri(BASE_URL)
                .pathParam("petId", petId)
                .when()
                .delete(PET_PATH + PET_ID);
    }
}
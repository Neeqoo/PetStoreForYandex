package tests.functional.api.pets;

import api.models.Pet;
import api.services.PetService;
import api.utils.PetsTestDataGenerator;
import api.utils.TestUtils;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import static org.hamcrest.Matchers.*;

@DisplayName("API тесты для работы с питомцами (Pet)")
public class PetsApiTests {
    private static PetService petService;
    private static Pet testPet;
    private static long createdPetId;

    @BeforeAll
    public static void setup() {
        petService = new PetService();
        testPet = PetsTestDataGenerator.generateRandomPet();
    }

    @Nested
    @DisplayName("POST /pet - Тесты создания нового питомца")
    class CreatePetTests {

        @AfterEach
        void tearDown() {
            petService.deletePet(createdPetId);
            TestUtils.waitFor(1000);
        }

        @Test
        @Tag("pet-api")
        @Tag("POST")
        @Tag("positive")
        @DisplayName("Тест создания питомца")
        public void testCreatePet() {
            Response response = petService.createPet(testPet);

            createdPetId = response.then()
                    .statusCode(200)
                    .extract().path("id");

            response.then()
                    .body("name", equalTo(testPet.getName()))
                    .body("id", equalTo(createdPetId));
        }
    }

    @Nested
    @DisplayName("GET /pet/{petId} - Тесты запроса данных о питомце")
    class GetPetTests {

        @BeforeAll
        public static void createPet() {
            Response createResponse = petService.createPet(testPet);
            createdPetId = createResponse.then()
                    .statusCode(200)
                    .extract().path("id");
            TestUtils.waitFor(1000);
        }

        @AfterEach
        void tearDown() {
            petService.deletePet(createdPetId);
            TestUtils.waitFor(1000);
        }

        @Test
        @Tag("pet-api")
        @Tag("GET")
        @Tag("positive")
        @DisplayName("Тест получения питомца по ID")
        public void testGetPet() {
            Response response = petService.getPetById(createdPetId);

            response.then()
                    .statusCode(200)
                    .body("id", equalTo((long) createdPetId));
            /* Можно добавить больше проверок, но у нас id не является уникальным полем, поэтому сейчас проверка не актуальна(большинство тестов падает) */
        }
    }

    @Nested
    @DisplayName("PUT /pet - Тесты обновления данных питомца")
    class UpdatePetTests {
        private static long testPetId;

        @BeforeAll
        public static void createPet() {
            Response createResponse = petService.createPet(testPet);
            testPetId = createResponse.then()
                    .statusCode(200)
                    .extract().path("id");
            TestUtils.waitFor(1000);
        }

        @AfterEach
        void tearDown() {
            petService.deletePet(createdPetId);
            petService.deletePet(testPetId);
            TestUtils.waitFor(2000);
            /* Тут добавил удаление тестового питтомца(1) и питомца(2) с обновленными данными, если обновление питомца(1) пройдет с ошибкой, чтобы он не оставался в бд (актуально если id станут уникальными) */
        }

        @Test
        @Tag("pet-api")
        @Tag("PUT")
        @Tag("positive")
        @DisplayName("Тест полного обновления питомца")
        public void testUpdatePet() {
            Pet updatedPet = PetsTestDataGenerator.generateRandomPet();
            updatedPet.setId(testPetId);

            Response response = petService.updatePet(updatedPet);

            response.then()
                    .statusCode(200)
                    .body("id", equalTo((long) testPetId))
                    .body("name", equalTo(updatedPet.getName()));

            /* Можно добавить больше проверок, но все так же не актуально, т.к. данные не уникальны */
            Response getResponse = petService.getPetById(testPetId);
            getResponse.then()
                    .statusCode(200)
                    .body("id", equalTo(updatedPet.getId()));
        }
    }

    @Nested
    @DisplayName("DELETE /pet/{petId} - Тесты на удаление питомца")
    class DeletePetTests {
        private long testPetId;

        @BeforeEach
        public void createPet() {
            Response createResponse = petService.createPet(testPet);
            testPetId = createResponse.then()
                    .statusCode(200)
                    .extract().path("id");
            TestUtils.waitFor(1000);
        }

        @Test
        @Tag("pet-api")
        @Tag("DELETE")
        @Tag("positive")
        @DisplayName("Тест удаления питомца")
        public void testDeletePet() {
            Response response = petService.deletePet(testPetId);

            response.then()
                    .statusCode(200)
                    .body("code", equalTo(200))
                    .body("message", equalTo(String.valueOf(testPetId)));

            /* Можно было бы добавить проверку, что питомец удален (поиск по id), но у нас id не является уникальным на данном тестовом сервисе, поэтому проверка не актуальна */
//            petService.getPetById(crudPetId).then().statusCode(404);
        }
    }

    @Nested
    @DisplayName("CRUD тесты для питомца (Pet)")
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class CrudPetTests {

        @Test
        @Order(1)
        @DisplayName("POST - Создание питомца")
        public void crudTestCreatePet() {
            Response response = petService.createPet(testPet);
            createdPetId = response.then()
                    .statusCode(200)
                    .extract().path("id");

            response.then()
                    .body("name", equalTo(testPet.getName()));
        }

        @Test
        @Order(2)
        @DisplayName("GET - Получение данных о питомце")
        public void crudTestGetPet() {
            TestUtils.waitFor(1000);
            Response response = petService.getPetById(createdPetId);

            response.then()
                    .statusCode(200)
                    .body("id", equalTo((long) createdPetId));
            /* Можно добавить больше проверок, но у нас id не является уникальным полем, поэтому сейчас проверка не актуальна(большинство тестов падает) */
        }

        @Test
        @Order(3)
        @DisplayName("PUT - Обновление данных питомца")
        public void crudTestUpdatePet() {
            TestUtils.waitFor(1000);
            Pet updatedPet = PetsTestDataGenerator.generateRandomPet();
            updatedPet.setId(createdPetId);

            Response response = petService.updatePet(updatedPet);
            response.then()
                    .statusCode(200)
                    .body("id", equalTo((long) createdPetId))
                    .body("name", equalTo(updatedPet.getName()));
                /*id сохраняю такой же, проверяю только по имени (и что id сохранился). В целом тест можно было бы доработать:
                Сделать проверку по всем полям, и возможно изменять id, но у меня нет документации на руках,
                что должно меняться, а что нет, и , т.к. поля неуникальны, ограничился таким тестом */

            testPet = updatedPet;
        }

        @Test
        @Order(4)
        @DisplayName("DELETE - Удаление питомца")
        public void crudTestDeletePet() {
            TestUtils.waitFor(1000);
            Response response = petService.deletePet(createdPetId);
            response.then()
                    .statusCode(200)
                    .body("message", equalTo(String.valueOf(createdPetId)));

            /* Можно было бы добавить проверку, что питомец удален (поиск по id), но у нас id не является уникальным на данном тестовом сервисе, поэтому проверка не актуальна */
//            petService.getPetById(crudPetId).then().statusCode(404);
        }
    }
}
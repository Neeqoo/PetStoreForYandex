package tests.functional.api.users;

import api.models.User;
import api.services.UserService;
import api.utils.UserTestDataGenerator;
import api.utils.TestUtils;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@DisplayName("API тесты для работы с пользователями (User)")
public class UserApiTests {
    private static UserService userService;
    private static User testUser;
    private static String username;


    @BeforeAll
    public static void setup() {
        userService = new UserService();
        testUser = UserTestDataGenerator.generateRandomUser();
        username = testUser.getUsername();
    }

    @Nested
    @DisplayName("POST /user - Тесты создания нового пользователя")
    class CreateUserTests {

        @AfterEach
        void tearDown() {
            userService.deleteUser(username);
            TestUtils.waitFor(2000);
        }


        @Test
        @Tag("user-api")
        @Tag("POST")
        @Tag("positive")
        @DisplayName("Тест №1")
        public void testCreateUser() {
            Response response = userService.createUser(testUser);

            response.then()
                    .statusCode(200)
                    .body("code", equalTo(200))
                    .body("type", equalTo("unknown"))
                    .body("message", notNullValue());

            TestUtils.waitFor(1000);
        }
    }

    @Nested
    @DisplayName("GET /user/{username} - Тесты запроса данных о пользователе")
    class GetUserTests {

        @BeforeAll
        public static void createUser() {
            Response createResponse = userService.createUser(testUser);
            createResponse.then().statusCode(200);
            TestUtils.waitFor(2000);

        }

        @AfterEach
        void tearDown() throws InterruptedException {
            TestUtils.waitFor(2000);
            userService.deleteUser(username);
        }


        @Test
        @Tag("user-api")
        @Tag("GET")
        @Tag("positive")
        @DisplayName("Тест №1")
        public void testGetUser() {

            Response response = userService.getUser(username);

            response.then()
                    .statusCode(200)
                    .body("id", notNullValue())
                    .body("username", equalTo(testUser.getUsername()))
                    .body("firstName", notNullValue())
                    .body("lastName", notNullValue())
                    .body("email", notNullValue())
                    .body("password", notNullValue())
                    .body("phone", notNullValue())
                    .body("userStatus", notNullValue());
            /* Поля для проверки не подтягивал, т.к. по одному и тому же Api запросу может выдаваться несколько результатов (userName не уникальное поле) */
        }
    }


    @Nested
    @DisplayName("PUT /user/{username} - Тесты обновления данных у пользователей")
    class UpdateUserTests {

        @BeforeAll
        public static void createUser() {
            Response createResponse = userService.createUser(testUser);
            createResponse.then().statusCode(200);
            TestUtils.waitFor(2000);

        }

        @AfterEach
        void tearDown() throws InterruptedException {
            TestUtils.waitFor(2000);
            userService.deleteUser(username);
        }


        @Test
        @Tag("user-api")
        @Tag("PUT")
        @Tag("positive")
        @DisplayName("Тест №1")
        public void testUpdateUser() {

            User updatedUser = UserTestDataGenerator.generateRandomUser();

            Response response = userService.updateUser(username, updatedUser);

            response.then()
                    .statusCode(200)
                    .body("code", equalTo(200))
                    .body("type", equalTo("unknown"))
                    .body("message", notNullValue());

        /* Проверяем что обновление произошло, и пользователь с новым UserName имеется в системе.
         Если бы поле UserName было уникальным, можно было бы проверить все поля у измененного пользователя */
            Response getResponse = userService.getUser(updatedUser.getUsername());
            getResponse.then()
                    .statusCode(200)
                    .body("username", equalTo(updatedUser.getUsername()));

            /* Меняею данные для корректного удаления тестовых данных */
            testUser = updatedUser;
            username = testUser.getUsername();
        }
    }


    @Nested
    @DisplayName("DELETE /user/{username} - Тесты на удаление пользователя")
    class DeleteUserTests {

        @BeforeAll
        public static void createUser() {
            Response createResponse = userService.createUser(testUser);
            createResponse.then().statusCode(200);
            TestUtils.waitFor(2000);

        }

        @Test
        @Tag("user-api")
        @Tag("DELETE")
        @Tag("positive")
        @DisplayName("Тест №1")
        public void testDeleteUser() {

            Response response = userService.deleteUser(username);

            response.then()
                    .statusCode(200)
                    .body("code", equalTo(200))
                    .body("type", equalTo("unknown"))
                    .body("message", equalTo(username));

            /* Возможная проверка по удалению пользователя, если бы userName был уникальным. */
//        Response getResponse = userService.getUser(username);
//        getResponse.then()
//                .statusCode(404);
        }
    }

    @Nested
    @DisplayName("CRUD тесты для пользователя (user)")
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class CrudUserTests {

        @Test
        @Order(1)
        @DisplayName("POST - Создание пользователя")
        public void CrudTestCreateUser() {
            Response response = userService.createUser(testUser);

            response.then()
                    .statusCode(200)
                    .body("code", equalTo(200))
                    .body("type", equalTo("unknown"))
                    .body("message", notNullValue());
        }

        @Test
        @Order(2)
        @DisplayName("PUT - Запрос данных о пользователе")
        public void CrudTestGetUser() {
            TestUtils.waitFor(2000);
            Response response = userService.getUser(username);

            response.then()
                    .statusCode(200)
                    .body("id", notNullValue())
                    .body("username", equalTo(testUser.getUsername()))
                    .body("firstName", notNullValue())
                    .body("lastName", notNullValue())
                    .body("email", notNullValue())
                    .body("password", notNullValue())
                    .body("phone", notNullValue())
                    .body("userStatus", notNullValue());
            /* Поля для проверки не подтягивал, т.к. по одному и тому же Api запросу может выдаваться несколько результатов (userName не уникальное поле) */
        }

        @Test
        @Order(3)
        @DisplayName("GET - изменение данных пользователя")
        public void CrudTestUpdateUser() {
            TestUtils.waitFor(2000);
            User updatedUser = UserTestDataGenerator.generateRandomUser();
            Response response = userService.updateUser(username, updatedUser);
            response.then()
                    .statusCode(200)
                    .body("code", equalTo(200))
                    .body("type", equalTo("unknown"))
                    .body("message", notNullValue());

            Response getResponse = userService.getUser(updatedUser.getUsername());
            getResponse.then()
                    .statusCode(200)
                    .body("username", equalTo(updatedUser.getUsername()));

            testUser = updatedUser;
            username = testUser.getUsername();
        }

        @Test
        @Order(4)
        @DisplayName("DELETE - Удаление пользователя")
        public void CrudTestDeleteUser() {
            TestUtils.waitFor(2000);
            Response response = userService.deleteUser(username);
            response.then()
                    .statusCode(200)
                    .body("code", equalTo(200))
                    .body("type", equalTo("unknown"))
                    .body("message", equalTo(username));
        }

    }
}
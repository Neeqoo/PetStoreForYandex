package api.utils;

import api.models.User;
import com.github.javafaker.Faker;

public class UserTestDataGenerator {
    private static final Faker faker = new Faker();

    public static User generateRandomUser() {
        User user = new User();
        user.setUsername(faker.name().username());
        user.setFirstName(faker.name().firstName());
        user.setLastName(faker.name().lastName());
        user.setEmail(faker.internet().emailAddress());
        user.setPassword(faker.internet().password());
        user.setPhone(faker.phoneNumber().phoneNumber());
        return user;
        /* id и userStatus зафиксировал на 0, т.к., при id=0, система самостоятельно назначает id, а userStatus, как я понял, это права пользователя, поэтому так же в рандомайзер добавлять не стал */
    }
}
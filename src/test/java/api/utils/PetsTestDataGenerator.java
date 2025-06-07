package api.utils;

import api.models.Pet;
import api.models.Pet.Category;
import api.models.Pet.Tag;
import com.github.javafaker.Faker;

import java.util.Arrays;
import java.util.List;

public class PetsTestDataGenerator {
    private static final Faker faker = new Faker();

    public static Pet generateRandomPet() {

        Pet pet = new Pet();
        pet.setId(0L);

        pet.setName(faker.funnyName().name());
        pet.setStatus(faker.options().option("available", "pending", "sold"));

        Category category = new Category();
        category.setId((long) faker.number().numberBetween(1, 100));
        category.setName(faker.animal().name());
        pet.setCategory(category);

        List<String> photoUrls = Arrays.asList(
                faker.internet().image(),
                faker.internet().image()
        );
        pet.setPhotoUrls(photoUrls);

        Tag tag1 = new Tag();
        tag1.setId((long) faker.number().numberBetween(1, 50));
        tag1.setName(faker.lorem().word());

        Tag tag2 = new Tag();
        tag2.setId((long) faker.number().numberBetween(51, 100));
        tag2.setName(faker.lorem().word());

        pet.setTags(Arrays.asList(tag1, tag2));

        return pet;
    }
}
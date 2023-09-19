package petstore.api.test;

import api.dto.*;
import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AssurePetTest {

    public static int generateRandomInt() {
        Faker faker = new Faker();
        return faker.number().randomDigit();
    }

    Faker faker = new Faker();
    int id = 55;
    CreateCategory category = new CreateCategory(generateRandomInt(),faker.address().cityName());
    CreateCategory updateCategory = new CreateCategory(generateRandomInt(),faker.address().cityName());
    String name = faker.pokemon().name();
    List<CreateTagsDto> tags = new ArrayList<>(Collections.singleton(new CreateTagsDto(generateRandomInt(), faker.address().country())));
    List<String> photoUrls = new ArrayList<>(Collections.singleton(faker.internet().image()));
    String status = "available";

    @BeforeMethod
    public void precondition() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2/";
        RestAssured.basePath = "pet";
    }
    @Test(priority = 1)
    public void createdUserAssure() {
        CreatePetRequestDto requestDto = CreatePetRequestDto.builder()
                .id(id)
                .category(category)
                .name(name)
                .status(status)
                .photoUrls(photoUrls)
                .tags(tags)
                .build();

        CreatePetResponseDto createPetResponse = RestAssured.given()
                .contentType("application/json")
                .body(requestDto)
                .post()
                .then()
                .assertThat().statusCode(200)
                .extract().response().as(CreatePetResponseDto.class);


        System.out.println(requestDto.getId());
        System.out.println(requestDto);

        String msg =  RestAssured.given()
                .contentType("application/json")
                .body(requestDto)
                .post()
                .then()
                .assertThat().body(Matchers.containsString("name"))
                .extract().path("name");
        System.out.println(msg);
    }

    @Test(priority = 2)
    public void getUserAssure() {
        RestAssured.basePath = "pet/" + id;
        CreatePetRequestDto requestDto = CreatePetRequestDto.builder()
                .id(id)
                .build();

        CreatePetResponseDto createPetResponse = RestAssured.given()
                .contentType("application/json")
                .body(requestDto)
                .get()
                .then()
                .assertThat().statusCode(200)
                .extract().response().as(CreatePetResponseDto.class);
        System.out.println(createPetResponse);
    }

    @Test(priority = 3)
    public void updatePetAssure() {
        UpdatePetDto requestDto = UpdatePetDto.builder()
                .id(id)
                .category(updateCategory)
                .name(faker.pokemon().name())
                .status(status)
                .photoUrls(new ArrayList<>(Collections.singleton(faker.internet().image())))
                .tags(new ArrayList<>(Collections.singleton(new CreateTagsDto(generateRandomInt(), "Change Name"))))
                .build();

        CreatePetResponseDto putPetResponse = RestAssured.given()
                .contentType("application/json")
                .body(requestDto)
                .put()
                .then()
                .assertThat().statusCode(200)
                .extract().response().as(CreatePetResponseDto.class);

        System.out.println(putPetResponse);
    }
    @Test
    public void deletePet() {
        RestAssured.basePath = "pet/" + id;
        DeletePetDto requestDto = DeletePetDto.builder()
                .id(id)
                .build();

        DeletePetResponseDto deletePetResponse = RestAssured.given()
                .contentType("application/json")
                .body(requestDto)
                .delete()
                .then()
                .assertThat().statusCode(200)
                .assertThat().body(Matchers.containsString("message"))
                .extract().response().as(DeletePetResponseDto.class);
        System.out.println(deletePetResponse);
    }
}
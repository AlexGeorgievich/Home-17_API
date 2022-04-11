package tests;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RestApiTests {

    @Test
    void checkStatus200() {
        get("https://reqres.in/api/users/2")
                .then()
                .statusCode(200)
                .body("data.first_name",is("Janet"));
    }

    @Test
    void checkStatus404() {
        get("https://reqres.in/api/users/23")
                .then()
                .statusCode(404);
    }
    @Test
    void successfulRegisterTest() {
        String authorisedData =  "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"pistol\" }";
        given()
                .body(authorisedData)
                .contentType(JSON)
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .statusCode(200)
                .body("token",is("QpwL5tke4Pnpja7X4"));
    }


    @Test
    void unsuccessfulRegisterTest() {
        String authorisedData = "{ \"email\": \"sydney@fife\" }";
        given()
                .body(authorisedData)
                .contentType(ContentType.JSON)
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .statusCode(400)
                .body("error",is("Missing password"));
    }

    @Test
    void chechAvatatAndIdTest() {
        List<UserData> users = given()
                .when()
                .contentType(ContentType.JSON)
                .get("https://reqres.in/api/users?page=2")
                .then().log().all()
                .extract().body().jsonPath().getList("data", UserData.class);
// все email оканчиваются на @reqres.in
        assertTrue(users.stream().allMatch(x -> x.getEmail().endsWith("@reqres.in")));
    }

}

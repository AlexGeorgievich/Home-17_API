package tests;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;

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
                .contentType(JSON)
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .statusCode(400)
                .body("error",is("Missing password"));
    }

}

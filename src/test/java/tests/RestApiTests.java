package tests;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.get;
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

}

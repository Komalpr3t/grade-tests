import io.restassured.RestAssured;
import io.restassured.response.Response;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class GradeApiTest {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }

    @Test
    public void getAllPostsTest() {

        Response response =
                given()
                        .when()
                        .get("/posts");

        response.then().statusCode(200);

        Assert.assertTrue(
                response.jsonPath().getList("$").size() > 0
        );
    }

    @Test
    public void getSinglePostTest() {

        Response response =
                given()
                        .when()
                        .get("/posts/1");

        response.then().statusCode(200);

        Assert.assertEquals(
                response.jsonPath().getInt("id"),
                1
        );

        Assert.assertFalse(
                response.jsonPath().getString("title").isEmpty()
        );
    }

    @Test
    public void createPostTest() {

        String requestBody = """
            {
              "title": "CSV302 Test",
              "body": "Automation Testing",
              "userId": 1
            }
            """;

        Response response =
                given()
                        .header("Content-Type", "application/json")
                        .body(requestBody)
                        .when()
                        .post("/posts");

        response.then().statusCode(201);

        Assert.assertEquals(
                response.jsonPath().getString("title"),
                "CSV302 Test"
        );
    }
    @Test
    public void invalidPostTest() {

        given()
                .when()
                .get("/posts/99999")
                .then()
                .statusCode(404);
    }

    @Test
    public void deletePostTest() {

        given()
                .when()
                .delete("/posts/1")
                .then()
                .statusCode(200);
    }

}
package ip.swagger.petstore;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(OrderAnnotation.class)
public class PetstoreApiTests {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost:8080/api/v3";
    }

    // Pets API tests

    @Test
    @Order(1)
    public void testAddPet() {
        String newPetJson = "{ \"id\": 1, \"name\": \"Doggie\", \"status\": \"available\" }";
        given()
                .contentType(ContentType.JSON)
                .body(newPetJson)
                .when()
                .post("/pet")
                .then()
                .statusCode(200)
                .body("name", equalTo("Doggie"))
                .body("status", equalTo("available"));
    }

    @Test
    @Order(2)
    public void testGetPetById() {
        int petId = 1; // Existing pet ID
        given()
                .pathParam("petId", petId)
                .when()
                .get("/pet/{petId}")
                .then()
                .statusCode(200)
                .body("id", equalTo(petId));
    }

    @Test
    @Order(3)
    public void testUpdatePet() {
        String updatedPetJson = "{ \"id\": 1, \"name\": \"Doggie\", \"status\": \"sold\" }";
        given()
                .contentType(ContentType.JSON)
                .body(updatedPetJson)
                .when()
                .put("/pet")
                .then()
                .statusCode(200)
                .body("status", equalTo("sold"));
    }

    @Test
    @Order(4)
    public void testDeletePet() {
        int petId = 1; // Existing pet ID to delete
        given()
                .pathParam("petId", petId)
                .when()
                .delete("/pet/{petId}")
                .then()
                .statusCode(200);
                //.body("message", equalTo(String.valueOf(petId)));
    }

    @Test
    @Order(5)
    public void testFindPetsByStatus() {
        String status = "available";
        given()
                .queryParam("status", status)
                .when()
                .get("/pet/findByStatus")
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0));
    }

    @Test
    @Order(6)
    public void test_06_FindPetsByTags() {
        String tag = "dog"; // Example tag
        given()
                .queryParam("tags", tag)
                .when()
                .get("/pet/findByTags")
                .then()
                .statusCode(200)
                .body("size()", greaterThanOrEqualTo(0));
    }

    // Store API tests

    @Test
    @Order(7)
    public void testGetInventory() {
        given()
                .when()
                .get("/store/inventory")
                .then()
                .statusCode(200);
    }

    @Test
    @Order(8)
    public void testPlaceOrder() {
        String orderJson = "{ \"id\": 1, \"petId\": 1, \"quantity\": 1, \"shipDate\": \"2024-10-10T00:00:00Z\", \"status\": \"placed\", \"complete\": false }";
        given()
                .contentType(ContentType.JSON)
                .body(orderJson)
                .when()
                .post("/store/order")
                .then()
                .statusCode(200)
                .body("status", equalTo("placed"));
    }

    @Test
    @Order(9)
    public void testGetOrderById() {
        int orderId = 1; // Example order ID
        given()
                .pathParam("orderId", orderId)
                .when()
                .get("/store/order/{orderId}")
                .then()
                .statusCode(200)
                .body("id", equalTo(orderId));
    }

    @Test
    @Order(10)
    public void testDeleteOrder() {
        int orderId = 1; // Example order ID to delete
        given()
                .pathParam("orderId", orderId)
                .when()
                .delete("/store/order/{orderId}")
                .then()
                .statusCode(200);
    }

    // User API tests

    @Test
    @Order(11)
    public void testCreateUser() {
        String userJson = "{ \"id\": 0, \"username\": \"johndoe\", \"firstName\": \"John\", \"lastName\": \"Doe\", \"email\": \"john@example.com\", \"password\": \"password\", \"phone\": \"123-456-7890\", \"userStatus\": 1 }";
        given()
                .contentType(ContentType.JSON)
                .body(userJson)
                .when()
                .post("/user")
                .then()
                .statusCode(200);
    }

    @Test
    @Order(12)
    public void testGetUserByUsername() {
        String username = "johndoe"; // Existing username
        given()
                .pathParam("username", username)
                .when()
                .get("/user/{username}")
                .then()
                .statusCode(200)
                .body("username", equalTo(username));
    }

    @Test
    @Order(13)
    public void testUpdateUser() {
        String updatedUserJson = "{ \"id\": 0, \"username\": \"johndoe\", \"firstName\": \"Johnny\", \"lastName\": \"Doe\", \"email\": \"johnny@example.com\", \"password\": \"newpassword\", \"phone\": \"123-456-7890\", \"userStatus\": 1 }";
        given()
                .contentType(ContentType.JSON)
                .body(updatedUserJson)
                .when()
                .put("/user/johndoe")
                .then()
                .statusCode(200);
    }

    @Test
    @Order(14)
    public void testDeleteUser() {
        String username = "johndoe"; // Existing username to delete
        given()
                .pathParam("username", username)
                .when()
                .delete("/user/{username}")
                .then()
                .statusCode(200);
    }

    @Test
    @Order(15)
    public void testLoginUser() {
        String username = "johndoe";
        String password = "password";
        given()
                .queryParam("username", username)
                .queryParam("password", password)
                .when()
                .get("/user/login")
                .then()
                .statusCode(200);
    }

    @Test
    @Order(16)
    public void testLogoutUser() {
        given()
                .when()
                .get("/user/logout")
                .then()
                .statusCode(200);
    }

    //BUG #001
    // Api is returning 200 when we send a request without user and password parameters. It should return 400
    @Test
    @Order(17)
    public void testLogin_MissingUserAndPassword() {
        given()
                .when()
                .get("/user/login")
                .then()
                .statusCode(400);
    }

    //BUG #002
    // Api is returning 200 when we send a request with an empty user and password parameters. It should return 400
    @Test
    @Order(18)
    public void testLogin_InvalidUserAndPassword() {
        String username = " ";
        String password = " ";
        given()
                .queryParam("username", username)
                .queryParam("password", password)
                .when()
                .get("/user/login")
                .then()
                .statusCode(400);
    }

    //#BUG #003
    // Api is returning 200 when we send a request without an username. It should return 400
    @Test
    @Order(19)
    public void testCreateUser_missingUsername() {
        String userJson = "{ \"id\": 0, \"firstName\": \"John\", \"lastName\": \"Doe\", \"email\": \"john@example.com\", \"password\": \"password\", \"phone\": \"123-456-7890\", \"userStatus\": 1 }";
        given()
                .contentType(ContentType.JSON)
                .body(userJson)
                .when()
                .post("/user")
                .then()
                .statusCode(400);
    }

    //BUG #004
    // Api is crashing after returning 500 when we send a request without providing a password. It should return 400
    @Test
    @Order(20)
    public void testCreateUser_missingPassword() {
        String userJson = "{ \"id\": 0, \"username\": \"johndoe\", \"firstName\": \"John\", \"lastName\": \"Doe\", \"email\": \"john@example.com\", \"phone\": \"123-456-7890\", \"userStatus\": 1 }";
        given()
                .contentType(ContentType.JSON)
                .body(userJson)
                .when()
                .post("/user")
                .then()
                .statusCode(400);
    }

}

package pl.lodz.p.it.tks.rent.rest.resources;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;
import pl.lodz.p.it.tks.rent.rest.AbstractContainer;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.notNullValue;

@Testcontainers
public class RentTestsByCustomerTests extends AbstractContainer {
    private static String JWT_TOKEN;

    @BeforeAll
    public static void setup() {
        getService();
        RestAssured.port = serviceOne.getMappedPort(8181);
        RestAssured.useRelaxedHTTPSValidation();

        JWT_TOKEN = login("TestEmployee", "zaq1@WSX");
    }

    public static String login(String username, String password) {
        RestAssured.port = serviceOne.getMappedPort(8181);
        RestAssured.useRelaxedHTTPSValidation();

        RestAssured.baseURI = "https://localhost/UserServiceApp-1.0-SNAPSHOT/api/";
        JSONObject jsonObj = new JSONObject()
                .put("login", username)
                .put("password", password);

        Response r = given().contentType(ContentType.JSON)
                .body(jsonObj.toString())
                .post("auth/login")
                .then()
                .statusCode(202)
                .extract()
                .response();

        RestAssured.baseURI = "https://localhost/RentServiceApp-1.0-SNAPSHOT/api/";

        return r.getBody().asString();
    }

    @Test
    public void addRentTest() {
        JSONObject testCustomer = addTestCustomer();
        JSONObject testCar = addTestCar();

        String testCustomerToken = login(testCustomer.getString("login"), "zaq1@WSX");

        String updatedDate = LocalDateTime.now().toString();

        JSONObject jsonObj = new JSONObject()
                .put("customerId", testCustomer.getString("id"))
                .put("carId", testCar.getString("id"))
                .put("rentStartDate", updatedDate);

        given().contentType(ContentType.JSON)
                .body(jsonObj.toString())
                .header(new Header("Authorization", "Bearer " + testCustomerToken))
                .post("rent")
                .then()
                .assertThat()
                .body("customerId", containsString(testCustomer.getString("id")))
                .body("carId", containsString(testCar.getString("id")))
                .body("rentStartDate", notNullValue())
                .statusCode(200);
    }

    @Test
    public void getRentTest() {
        JSONObject testCustomer = addTestCustomer();
        JSONObject testCar = addTestCar();

        String testCustomerToken = login(testCustomer.getString("login"), "zaq1@WSX");

        JSONObject testRent = addTestRent(testCustomer.getString("id"), testCar.getString("id"), testCustomerToken);

        given().contentType(ContentType.JSON)
                .header(new Header("Authorization", "Bearer " + JWT_TOKEN))
                .get("rent/" + testRent.getString("id"))
                .then()
                .assertThat()
                .body("customerId", containsString(testRent.getString("customerId")))
                .body("carId", containsString(testRent.getString("carId")))
                .body("rentStartDate", containsString(testRent.getString("rentStartDate")))
                .statusCode(200);
    }

    @Test
    public void getAllRentsTest() {
        JSONObject testCustomer = addTestCustomer();
        JSONObject testCar1 = addTestCar();
        JSONObject testCar2 = addTestCar();

        String testCustomerToken = login(testCustomer.getString("login"), "zaq1@WSX");

        addTestRent(testCustomer.getString("id"), testCar1.getString("id"), testCustomerToken);
        addTestRent(testCustomer.getString("id"), testCar2.getString("id"), testCustomerToken);

        JSONArray customersReservations = new JSONArray(
                given().contentType(ContentType.JSON)
                        .header(new Header("Authorization", "Bearer " + JWT_TOKEN))
                        .get("rent")
                        .then()
                        .extract()
                        .body()
                        .asString()
        );

        int lastIndex = customersReservations.length() - 1;
        Assertions.assertEquals(customersReservations.getJSONObject(lastIndex).getString("customerId"), testCustomer.getString("id"));
        Assertions.assertEquals(customersReservations.getJSONObject(lastIndex - 1).getString("customerId"), testCustomer.getString("id"));
    }

    public JSONObject addTestCustomer() {
        int randomNum = ThreadLocalRandom.current().nextInt(112312, 888888);
        JSONObject jsonObj = new JSONObject()
                .put("login","TestRestCustomer" + randomNum)
                .put("firstname","TestRestCustomer")
                .put("lastname","TestRestCustomer");

        JSONObject userServiceObj = new JSONObject(jsonObj.toString()).put("userType", "CUSTOMER").put("password", "zaq1@WSX");

        RestAssured.baseURI = "https://localhost/UserServiceApp-1.0-SNAPSHOT/api/";
        given().contentType(ContentType.JSON)
                .body(userServiceObj.toString())
                .header(new Header("Authorization", "Bearer " + JWT_TOKEN))
                .post("user")
                .then()
                .extract()
                .body()
                .asString();

        String obj = given().contentType(ContentType.JSON)
                .header(new Header("Authorization", "Bearer " + JWT_TOKEN))
                .get("user/" + jsonObj.getString("login"))
                .then()
                .extract()
                .body()
                .asString();

        RestAssured.baseURI = "https://localhost/RentServiceApp-1.0-SNAPSHOT/api/";
        return new JSONObject(obj);
    }

    public JSONObject addTestCar() {
        int randomNum = ThreadLocalRandom.current().nextInt(112312, 888888);
        JSONObject jsonObj = new JSONObject()
                .put("engineCapacity", 1.5)
                .put("vin", "12345678901" + randomNum)
                .put("doorNumber", 5)
                .put("brand", "TestBrand" + randomNum)
                .put("basePricePerDay", 1000d)
                .put("driverEquipment","TestEqupiment");

        return new JSONObject(
                given().contentType(ContentType.JSON)
                        .body(jsonObj.toString())
                        .header(new Header("Authorization", "Bearer " + JWT_TOKEN))
                        .post("car/economy")
                        .then()
                        .extract()
                        .body()
                        .asString()
        );
    }

    public JSONObject addTestRent(String customerId, String carId, String jwtToken) {
        String updatedDate = LocalDateTime.now().toString();

        JSONObject jsonObj = new JSONObject()
                .put("customerId", customerId)
                .put("carId", carId)
                .put("rentStartDate", updatedDate);

        return new JSONObject(
                given().contentType(ContentType.JSON)
                        .body(jsonObj.toString())
                        .header(new Header("Authorization", "Bearer " + jwtToken))
                        .post("rent")
                        .then()
                        .extract()
                        .body()
                        .asString()
        );
    }
}

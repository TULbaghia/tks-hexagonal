package pl.lodz.p.it.tks.rest.resources;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.notNullValue;

public class RentTestsByCustomer {
    private String JWT_TOKEN;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://localhost/REST-1.0-SNAPSHOT/api/";
        RestAssured.port = 8181;
        RestAssured.useRelaxedHTTPSValidation();

        JWT_TOKEN = login("TestEmployee", "zaq1@WSX");
    }

    public String login(String username, String password) {
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
                .body("customer.id", containsString(testCustomer.getString("id")))
                .body("car.id", containsString(testCar.getString("id")))
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
                .body("customer.id", containsString(testRent.getJSONObject("customer").getString("id")))
                .body("car.id", containsString(testRent.getJSONObject("car").getString("id")))
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
        Assert.assertEquals(customersReservations.getJSONObject(lastIndex).getJSONObject("customer").getString("login"), testCustomer.getString("login"));
        Assert.assertEquals(customersReservations.getJSONObject(lastIndex - 1).getJSONObject("customer").getString("login"), testCustomer.getString("login"));
    }

    public JSONObject addTestCustomer() {
        int randomNum = ThreadLocalRandom.current().nextInt(112312, 888888);
        JSONObject jsonObj = new JSONObject()
                .put("login","TestCaseUser" + randomNum)
                .put("password","zaq1@WSX")
                .put("firstname","TestCaseUser")
                .put("lastname","TestCaseUser");

        return new JSONObject(
                given().contentType(ContentType.JSON)
                        .body(jsonObj.toString())
                        .header(new Header("Authorization", "Bearer " + JWT_TOKEN))
                        .post("user/customer")
                        .then()
                        .extract()
                        .body()
                        .asString()
        );
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

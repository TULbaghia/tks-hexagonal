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

import java.util.concurrent.ThreadLocalRandom;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

@Testcontainers
public class ExclusiveCarTests extends AbstractContainer {
    private static String JWT_TOKEN;

    @BeforeAll
    public static void setup() {
        getService();
        RestAssured.baseURI = "https://localhost/UserServiceApp-1.0-SNAPSHOT/api/";
        RestAssured.port = serviceOne.getMappedPort(8181);
        RestAssured.useRelaxedHTTPSValidation();

        JSONObject jsonObj = new JSONObject()
                .put("login","TestEmployee")
                .put("password","zaq1@WSX");

        Response r = given().contentType(ContentType.JSON)
                .body(jsonObj.toString())
                .post("auth/login")
                .then()
                .statusCode(202)
                .extract()
                .response();

        RestAssured.baseURI = "https://localhost/RentServiceApp-1.0-SNAPSHOT/api/";

        JWT_TOKEN = r.getBody().asString();
    }

    @Test
    public void addExclusiveCarTest() {
        int randomNum = ThreadLocalRandom.current().nextInt(112312, 888888);
        JSONObject jsonObj = new JSONObject()
                .put("engineCapacity", 1.5)
                .put("vin", "12345678901" + randomNum)
                .put("doorNumber", 5)
                .put("brand", "TestBrand" + randomNum)
                .put("basePricePerDay", 1000.00)
                .put("driverEquipment","TestEquipment")
                .put("boardPcName", "TestBoardPcName");

        given().contentType(ContentType.JSON)
                .body(jsonObj.toString())
                .header(new Header("Authorization", "Bearer " + JWT_TOKEN))
                .post("car/exclusive")
                .then()
                .assertThat()
                .body("engineCapacity", equalTo(jsonObj.getFloat("engineCapacity")))
                .body("vin", containsString(jsonObj.getString("vin")))
                .body("doorNumber", equalTo(jsonObj.getInt("doorNumber")))
                .body("brand", containsString(jsonObj.getString("brand")))
                .body("basePricePerDay", equalTo(jsonObj.getInt("basePricePerDay")))
                .body("driverEquipment", containsString(jsonObj.getString("driverEquipment")))
                .body("boardPcName", containsString(jsonObj.getString("boardPcName")))
                .statusCode(200);
    }

    @Test
    public void getExclusiveCarTest() {
        JSONObject exclusiveCar = addTestCar();

        given().contentType(ContentType.JSON)
                .header(new Header("Authorization", "Bearer " + JWT_TOKEN))
                .get("car/exclusive/" + exclusiveCar.getString("id"))
                .then()
                .assertThat()
                .body("engineCapacity", equalTo(exclusiveCar.getFloat("engineCapacity")))
                .body("vin", containsString(exclusiveCar.getString("vin")))
                .body("doorNumber", equalTo(exclusiveCar.getInt("doorNumber")))
                .body("brand", containsString(exclusiveCar.getString("brand")))
                .body("basePricePerDay", equalTo(exclusiveCar.getInt("basePricePerDay")))
                .body("driverEquipment", containsString(exclusiveCar.getString("driverEquipment")))
                .body("boardPcName", containsString(exclusiveCar.getString("boardPcName")))
                .statusCode(200);
    }

    @Test
    public void getAllExclusiveCarsTest() {
        JSONObject testCar1 = addTestCar();
        JSONObject testCar2 = addTestCar();
        JSONObject testCar3 = addTestCar();

        JSONArray exclusiveCars = new JSONArray(
                given().contentType(ContentType.JSON)
                        .header(new Header("Authorization", "Bearer " + JWT_TOKEN))
                        .get("car/exclusive")
                        .then()
                        .extract()
                        .body()
                        .asString()
        );

        int lastIndex = exclusiveCars.length() - 1;
        Assertions.assertEquals(exclusiveCars.getJSONObject(lastIndex).getString("id"), testCar3.getString("id"));
        Assertions.assertEquals(exclusiveCars.getJSONObject(lastIndex - 1).getString("id"), testCar2.getString("id"));
        Assertions.assertEquals(exclusiveCars.getJSONObject(lastIndex - 2).getString("id"), testCar1.getString("id"));
    }

    @Test
    public void updateExclusiveCarTest() {
        int randomNum = ThreadLocalRandom.current().nextInt(112312, 888888);
        JSONObject testCar = addTestCar();
        JSONObject jsonObj = new JSONObject()
                .put("id", testCar.getString("id"))
                .put("engineCapacity", 1.5)
                .put("vin", "12345678901" + randomNum)
                .put("doorNumber", 5)
                .put("brand", "TestBrand" + randomNum)
                .put("basePricePerDay", 1000.00)
                .put("driverEquipment", "UpdatedEquipment")
                .put("boardPcName", "UpdatedBoardPcName");

        given().contentType(ContentType.JSON)
                .body(jsonObj.toString())
                .header(new Header("Authorization", "Bearer " + JWT_TOKEN))
                .put("car/exclusive")
                .then()
                .assertThat()
                .body("engineCapacity", equalTo(jsonObj.getFloat("engineCapacity")))
                .body("vin", containsString(jsonObj.getString("vin")))
                .body("doorNumber", equalTo(jsonObj.getInt("doorNumber")))
                .body("brand", containsString(jsonObj.getString("brand")))
                .body("basePricePerDay", equalTo(jsonObj.getInt("basePricePerDay")))
                .body("driverEquipment", containsString(jsonObj.getString("driverEquipment")))
                .body("boardPcName", containsString(jsonObj.getString("boardPcName")))
                .statusCode(200);
    }

    @Test
    public void deleteExclusiveCarTest() {
        JSONObject testCar = addTestCar();

        given().contentType(ContentType.JSON)
                .header(new Header("Authorization", "Bearer " + JWT_TOKEN))
                .delete("car/exclusive/" + testCar.getString("id"))
                .then()
                .assertThat()
                .body("success", equalTo(true))
                .statusCode(200);
    }

    public JSONObject addTestCar() {
        int randomNum = ThreadLocalRandom.current().nextInt(112312, 888888);
        JSONObject jsonObj = new JSONObject()
                .put("engineCapacity", 1.5)
                .put("vin", "12345678901" + randomNum)
                .put("doorNumber", 5)
                .put("brand", "TestBrand" + randomNum)
                .put("basePricePerDay", 1000d)
                .put("driverEquipment", "TestEqupiment")
                .put("boardPcName", "TestPcName");

        return new JSONObject(
                given().contentType(ContentType.JSON)
                        .body(jsonObj.toString())
                        .header(new Header("Authorization", "Bearer " + JWT_TOKEN))
                        .post("car/exclusive")
                        .then()
                        .extract()
                        .body()
                        .asString()
        );
    }
}

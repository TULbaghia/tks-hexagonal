package pl.lodz.p.it.tks.rent.rest.resources;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.ThreadLocalRandom;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class EconomyCarTests {
    private String JWT_TOKEN;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://localhost/REST-1.0-SNAPSHOT/api/";
        RestAssured.port = 8181;
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

        JWT_TOKEN = r.getBody().asString();
    }

    @Test
    public void addEconomyCarTest() {
        int randomNum = ThreadLocalRandom.current().nextInt(112312, 888888);
        JSONObject jsonObj = new JSONObject()
                .put("engineCapacity", 1.5)
                .put("vin", "12345678901" + randomNum)
                .put("doorNumber", 5)
                .put("brand", "TestBrand" + randomNum)
                .put("basePricePerDay", 1000.00)
                .put("driverEquipment","TestEquipment");

        given().contentType(ContentType.JSON)
                .body(jsonObj.toString())
                .header(new Header("Authorization", "Bearer " + JWT_TOKEN))
                .post("car/economy")
                .then()
                .assertThat()
                .body("engineCapacity", equalTo(jsonObj.getFloat("engineCapacity")))
                .body("doorNumber", equalTo(jsonObj.getInt("doorNumber")))
                .body("brand", containsString(jsonObj.getString("brand")))
                .body("basePricePerDay", equalTo(jsonObj.getInt("basePricePerDay")))
                .body("vin", containsString(jsonObj.getString("vin")))
                .body("driverEquipment", containsString(jsonObj.getString("driverEquipment")))
                .statusCode(200);
    }

    @Test
    public void getEconomyCarTest() {
        JSONObject economyCar = addTestCar();

        given().contentType(ContentType.JSON)
                .header(new Header("Authorization", "Bearer " + JWT_TOKEN))
                .get("car/economy/" + economyCar.getString("id"))
                .then()
                .assertThat()
                .body("engineCapacity", equalTo(economyCar.getFloat("engineCapacity")))
                .body("doorNumber", equalTo(economyCar.getInt("doorNumber")))
                .body("brand", containsString(economyCar.getString("brand")))
                .body("basePricePerDay", equalTo(economyCar.getInt("basePricePerDay")))
                .body("vin", containsString(economyCar.getString("vin")))
                .body("driverEquipment", containsString(economyCar.getString("driverEquipment")))
                .statusCode(200);
    }

    @Test
    public void getAllEconomyCarsTest() {
        JSONObject testCar1 = addTestCar();
        JSONObject testCar2 = addTestCar();
        JSONObject testCar3 = addTestCar();

        JSONArray economyCars = new JSONArray(
                given().contentType(ContentType.JSON)
                        .header(new Header("Authorization", "Bearer " + JWT_TOKEN))
                        .get("car/economy")
                        .then()
                        .extract()
                        .body()
                        .asString()
        );


        int lastIndex = economyCars.length() - 1;
        Assert.assertEquals(economyCars.getJSONObject(lastIndex).getString("id"), testCar3.getString("id"));
        Assert.assertEquals(economyCars.getJSONObject(lastIndex - 1).getString("id"), testCar2.getString("id"));
        Assert.assertEquals(economyCars.getJSONObject(lastIndex - 2).getString("id"), testCar1.getString("id"));
    }

    @Test
    public void updateEconomyCarTest() {
        int randomNum = ThreadLocalRandom.current().nextInt(112312, 888888);
        JSONObject testCar = addTestCar();
        JSONObject jsonObj = new JSONObject()
                .put("id", testCar.getString("id"))
                .put("vin", "12345678901" + randomNum)
                .put("engineCapacity", 1.5)
                .put("doorNumber", 5)
                .put("brand", "TestBrand" + randomNum)
                .put("basePricePerDay", 1000.00)
                .put("driverEquipment", "UpdatedEquipment");

        given().contentType(ContentType.JSON)
                .body(jsonObj.toString())
                .header(new Header("Authorization", "Bearer " + JWT_TOKEN))
                .put("car/economy")
                .then()
                .assertThat()
                .body("engineCapacity", equalTo(jsonObj.getFloat("engineCapacity")))
                .body("doorNumber", equalTo(jsonObj.getInt("doorNumber")))
                .body("brand", containsString(jsonObj.getString("brand")))
                .body("basePricePerDay", equalTo(jsonObj.getInt("basePricePerDay")))
                .body("vin", containsString(jsonObj.getString("vin")))
                .body("driverEquipment", containsString(jsonObj.getString("driverEquipment")))
                .statusCode(200);
    }

    @Test
    public void deleteEconomyCarTest() {
        JSONObject testCar = addTestCar();

        given().contentType(ContentType.JSON)
                .header(new Header("Authorization", "Bearer " + JWT_TOKEN))
                .delete("car/economy/" + testCar.getString("id"))
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
}

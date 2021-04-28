package pl.lodz.p.it.tks.rent.rest.user;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.ThreadLocalRandom;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class CustomerTests {

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
    public void addCustomerTest() {
        int randomNum = ThreadLocalRandom.current().nextInt(112312, 888888);
        JSONObject jsonObj = new JSONObject()
                .put("login","loginTest" + randomNum)
                .put("password","zaq1@WSX")
                .put("firstname","firstnameTest")
                .put("lastname","lastnameTest");

        given().contentType(ContentType.JSON)
                .body(jsonObj.toString())
                .header(new Header("Authorization", "Bearer " + JWT_TOKEN))
                .post("user/customer")
                .then()
                .assertThat()
                .body("login", containsString("loginTest" + randomNum))
                .body("firstname", containsString("firstnameTest"))
                .body("lastname", containsString("lastnameTest"))
                .body("active", equalTo(true))
                .statusCode(200);
    }

    @Test
    public void getAllCustomersTests() {
        JSONObject testCustomer1 = addTestCustomer();
        JSONObject testCustomer2 = addTestCustomer();
        JSONObject testCustomer3 = addTestCustomer();

        JSONArray customersArray = new JSONArray(given().contentType(ContentType.JSON)
                .header(new Header("Authorization", "Bearer " + JWT_TOKEN))
                .get("user/customer")
                .then()
                .extract()
                .body().asString());

        int lastIndex = customersArray.length() - 1;
        Assert.assertEquals(customersArray.getJSONObject(lastIndex).getString("id"), testCustomer3.getString("id"));
        Assert.assertEquals(customersArray.getJSONObject(lastIndex - 1).getString("id"), testCustomer2.getString("id"));
        Assert.assertEquals(customersArray.getJSONObject(lastIndex - 2).getString("id"), testCustomer1.getString("id"));
    }

    @Test
    public void getCustomerTest() {
        JSONObject testUser = addTestCustomer();
        given().contentType(ContentType.JSON)
                .header(new Header("Authorization", "Bearer " + JWT_TOKEN))
                .get("user/customer/" + testUser.getString("id"))
                .then()
                .assertThat()
                .body("login", containsString(testUser.getString("login")))
                .body("firstname", containsString(testUser.getString("firstname")))
                .body("lastname", containsString(testUser.getString("lastname")))
                .body("active", equalTo(true))
                .statusCode(200);
    }

    @Test
    public void updateCustomerTest() {
        JSONObject testUser = addTestCustomer();
        JSONObject jsonObj = new JSONObject()
                .put("id", testUser.get("id"))
                .put("login", testUser.get("login"))
                .put("password","zaq1@WSX")
                .put("firstname","UpdatedFirstname")
                .put("lastname","UpdatedLastname");

        given().contentType(ContentType.JSON)
                .body(jsonObj.toString())
                .header(new Header("Authorization", "Bearer " + JWT_TOKEN))
                .put("user/customer")
                .then()
                .assertThat()
                .body("login", containsString(testUser.getString("login")))
                .body("firstname", containsString("UpdatedFirstname"))
                .body("lastname", containsString("UpdatedLastname"))
                .body("active", equalTo(true))
                .statusCode(200);

        jsonObj = new JSONObject()
                .put("id", testUser.get("id"))
                .put("login", "TestCustomer")
                .put("password","zaq1@WSX")
                .put("firstname","UpdatedFirstname")
                .put("lastname","UpdatedLastname");

        given().contentType(ContentType.JSON)
                .body(jsonObj.toString())
                .header(new Header("Authorization", "Bearer " + JWT_TOKEN))
                .put("user/customer")
                .then()
                .assertThat()
                .body("constraint", containsString("User with this login already exists."))
                .statusCode(400);
    }

    @Test
    public void activateCustomerTest() {
        JSONObject testUser = addTestCustomer();
        JSONObject jsonObj = new JSONObject()
                .put("id", testUser.get("id"))
                .put("active", false);

        RequestSpecification rs = RestAssured.given();

        rs.contentType(ContentType.JSON)
                .body(jsonObj.toString())
                .header(new Header("Authorization", "Bearer " + JWT_TOKEN))
                .patch("user/customer")
                .then()
                .assertThat()
                .body("login", containsString(testUser.getString("login")))
                .body("firstname", containsString(testUser.getString("firstname")))
                .body("lastname", containsString(testUser.getString("lastname")))
                .body("active", equalTo(false))
                .statusCode(200);
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
}

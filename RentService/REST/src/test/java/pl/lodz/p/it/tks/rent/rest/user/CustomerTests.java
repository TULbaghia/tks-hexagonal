package pl.lodz.p.it.tks.rent.rest.user;

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
public class CustomerTests extends AbstractContainer {

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

//    @Test
//    public void addCustomerTest() {
//        int randomNum = ThreadLocalRandom.current().nextInt(112312, 888888);
//        JSONObject jsonObj = new JSONObject()
//                .put("login","AddCustomerTest" + randomNum)
//                .put("firstname","AddCustomerTest")
//                .put("lastname","AddCustomerTest");
//
//        JSONObject userServiceObj = new JSONObject(jsonObj.toString()).put("userType", "CUSTOMER").put("password", "zaq1@WSX");
//
//        RestAssured.baseURI = "https://localhost/UserServiceApp-1.0-SNAPSHOT/api/";
//        given().contentType(ContentType.JSON)
//                .body(userServiceObj.toString())
//                .header(new Header("Authorization", "Bearer " + JWT_TOKEN))
//                .post("user")
//                .then()
//                .assertThat()
//                .body("login", containsString("AddCustomerTest" + randomNum))
//                .body("firstname", containsString("AddCustomerTest"))
//                .body("lastname", containsString("AddCustomerTest"))
//                .body("active", equalTo(true))
//                .statusCode(200);
//
//        RestAssured.baseURI = "https://localhost/RentServiceApp-1.0-SNAPSHOT/api/";
//        given().contentType(ContentType.JSON)
//                .body(jsonObj.toString())
//                .header(new Header("Authorization", "Bearer " + JWT_TOKEN))
//                .post("user/customer")
//                .then()
//                .assertThat()
//                .body("login", containsString("AddCustomerTest" + randomNum))
//                .body("firstname", containsString("AddCustomerTest"))
//                .body("lastname", containsString("AddCustomerTest"))
//                .body("active", equalTo(true))
//                .statusCode(200);
//    }

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
        Assertions.assertEquals(customersArray.getJSONObject(lastIndex).getString("id"), testCustomer3.getString("id"));
        Assertions.assertEquals(customersArray.getJSONObject(lastIndex - 1).getString("id"), testCustomer2.getString("id"));
        Assertions.assertEquals(customersArray.getJSONObject(lastIndex - 2).getString("id"), testCustomer1.getString("id"));
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

//    @Test
//    public void updateCustomerTest() {
//        JSONObject testUser = addTestCustomer();
//        JSONObject jsonObj = new JSONObject()
//                .put("id", testUser.get("id"))
//                .put("login", testUser.get("login"))
//                .put("password","zaq1@WSX")
//                .put("firstname","UpdatedFirstname")
//                .put("lastname","UpdatedLastname");
//
//        given().contentType(ContentType.JSON)
//                .body(jsonObj.toString())
//                .header(new Header("Authorization", "Bearer " + JWT_TOKEN))
//                .put("user/customer")
//                .then()
//                .assertThat()
//                .body("login", containsString(testUser.getString("login")))
//                .body("firstname", containsString("UpdatedFirstname"))
//                .body("lastname", containsString("UpdatedLastname"))
//                .body("active", equalTo(true))
//                .statusCode(200);
//
//        jsonObj = new JSONObject()
//                .put("id", testUser.get("id"))
//                .put("login", "TestCustomer")
//                .put("password","zaq1@WSX")
//                .put("firstname","UpdatedFirstname")
//                .put("lastname","UpdatedLastname");
//
//        given().contentType(ContentType.JSON)
//                .body(jsonObj.toString())
//                .header(new Header("Authorization", "Bearer " + JWT_TOKEN))
//                .put("user/customer")
//                .then()
//                .assertThat()
//                .body("constraint", containsString("User with this login already exists."))
//                .statusCode(400);
//    }

//    @Test
//    public void activateCustomerTest() {
//        JSONObject testUser = addTestCustomer();
//        JSONObject jsonObj = new JSONObject()
//                .put("id", testUser.get("id"))
//                .put("active", false);
//
//        RequestSpecification rs = RestAssured.given();
//
//        rs.contentType(ContentType.JSON)
//                .body(jsonObj.toString())
//                .header(new Header("Authorization", "Bearer " + JWT_TOKEN))
//                .patch("user/customer")
//                .then()
//                .assertThat()
//                .body("login", containsString(testUser.getString("login")))
//                .body("firstname", containsString(testUser.getString("firstname")))
//                .body("lastname", containsString(testUser.getString("lastname")))
//                .body("active", equalTo(false))
//                .statusCode(200);
//    }

    public JSONObject addTestCustomer() {
        int randomNum = ThreadLocalRandom.current().nextInt(112312, 888888);
        JSONObject jsonObj = new JSONObject()
                .put("login","TestCaseCustomer" + randomNum)
                .put("firstname","TestCaseCustomer")
                .put("lastname","TestCaseCustomer");

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
}

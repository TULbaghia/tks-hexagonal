package pl.lodz.p.it.tks.rest.user;

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

public class EmployeeTests {

    private String JWT_TOKEN;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://localhost/REST-1.0-SNAPSHOT/api/";
        RestAssured.port = 8181;
        RestAssured.useRelaxedHTTPSValidation();

        JSONObject jsonObj = new JSONObject()
                .put("login","TestAdmin")
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
    public void addEmployeeTest() {
        int randomNum = ThreadLocalRandom.current().nextInt(112312, 888888);
        JSONObject jsonObj = new JSONObject()
                .put("login","loginTest" + randomNum)
                .put("password","zaq1@WSX")
                .put("firstname","firstnameTest")
                .put("lastname","lastnameTest");

        given().contentType(ContentType.JSON)
                .body(jsonObj.toString())
                .header(new Header("Authorization", "Bearer " + JWT_TOKEN))
                .post("user/employee")
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
        JSONObject testEmployee1 = addTestEmployee();
        JSONObject testEmployee2 = addTestEmployee();
        JSONObject testEmployee3 = addTestEmployee();

        JSONArray employeesArray = new JSONArray(given().contentType(ContentType.JSON)
                .header(new Header("Authorization", "Bearer " + JWT_TOKEN))
                .get("user/employee")
                .then()
                .extract()
                .body().asString());

        int lastIndex = employeesArray.length() - 1;
        Assert.assertEquals(employeesArray.getJSONObject(lastIndex).getString("id"), testEmployee3.getString("id"));
        Assert.assertEquals(employeesArray.getJSONObject(lastIndex - 1).getString("id"), testEmployee2.getString("id"));
        Assert.assertEquals(employeesArray.getJSONObject(lastIndex - 2).getString("id"), testEmployee1.getString("id"));
    }

    @Test
    public void getEmployeeTest() {
        JSONObject testUser = addTestEmployee();
        given().contentType(ContentType.JSON)
                .header(new Header("Authorization", "Bearer " + JWT_TOKEN))
                .get("user/employee/" + testUser.getString("id"))
                .then()
                .assertThat()
                .body("login", containsString(testUser.getString("login")))
                .body("firstname", containsString(testUser.getString("firstname")))
                .body("lastname", containsString(testUser.getString("lastname")))
                .body("active", equalTo(true))
                .statusCode(200);
    }

    @Test
    public void updateEmployeeTest() {
        JSONObject testUser = addTestEmployee();
        JSONObject jsonObj = new JSONObject()
                .put("id", testUser.get("id"))
                .put("login", testUser.get("login"))
                .put("password","zaq1@WSX")
                .put("firstname","UpdatedFirstname")
                .put("lastname","UpdatedLastname");

        given().contentType(ContentType.JSON)
                .body(jsonObj.toString())
                .header(new Header("Authorization", "Bearer " + JWT_TOKEN))
                .put("user/employee")
                .then()
                .assertThat()
                .body("login", containsString(testUser.getString("login")))
                .body("firstname", containsString("UpdatedFirstname"))
                .body("lastname", containsString("UpdatedLastname"))
                .body("active", equalTo(true))
                .statusCode(200);

        jsonObj = new JSONObject()
                .put("id", testUser.get("id"))
                .put("login", "TestEmployee")
                .put("password","zaq1@WSX")
                .put("firstname","UpdatedFirstname")
                .put("lastname","UpdatedLastname");

        given().contentType(ContentType.JSON)
                .body(jsonObj.toString())
                .header(new Header("Authorization", "Bearer " + JWT_TOKEN))
                .put("user/employee")
                .then()
                .assertThat()
                .body("constraint", containsString("User with this login already exists."))
                .statusCode(400);
    }

    @Test
    public void activateEmployeeTest() {
        JSONObject testUser = addTestEmployee();
        JSONObject jsonObj = new JSONObject()
                .put("id", testUser.get("id"))
                .put("isActive", false);

        RequestSpecification rs = RestAssured.given();

        rs.contentType(ContentType.JSON)
                .body(jsonObj.toString())
                .header(new Header("Authorization", "Bearer " + JWT_TOKEN))
                .patch("user/employee")
                .then()
                .assertThat()
                .body("login", containsString(testUser.getString("login")))
                .body("firstname", containsString(testUser.getString("firstname")))
                .body("lastname", containsString(testUser.getString("lastname")))
                .body("active", equalTo(false))
                .statusCode(200);
    }

    public JSONObject addTestEmployee() {
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
                        .post("user/employee")
                        .then()
                        .extract()
                        .body()
                        .asString()
        );
    }
}

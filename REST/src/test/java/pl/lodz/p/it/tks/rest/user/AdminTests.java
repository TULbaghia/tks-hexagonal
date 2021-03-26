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

public class AdminTests {

    private String JWT_TOKEN;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://localhost:8181/REST-1.0-SNAPSHOT/api/";
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
    public void addAdminTest() {
        int randomNum = ThreadLocalRandom.current().nextInt(112312, 888888);
        JSONObject jsonObj = new JSONObject()
                .put("login","loginTest" + randomNum)
                .put("password","zaq1@WSX")
                .put("firstname","firstnameTest")
                .put("lastname","lastnameTest");

        given().contentType(ContentType.JSON)
                .body(jsonObj.toString())
                .header(new Header("Authorization", "Bearer " + JWT_TOKEN))
                .post("user/admin")
                .then()
                .assertThat()
                .body("login", containsString("loginTest" + randomNum))
                .body("firstname", containsString("firstnameTest"))
                .body("lastname", containsString("lastnameTest"))
                .body("active", equalTo(true))
                .statusCode(200);
    }

    @Test
    public void getAllAdminsTests() {
        JSONObject testAdmin1 = addTestAdmin();
        JSONObject testAdmin2 = addTestAdmin();
        JSONObject testAdmin3 = addTestAdmin();

        JSONArray adminsArray = new JSONArray(given().contentType(ContentType.JSON)
                .header(new Header("Authorization", "Bearer " + JWT_TOKEN))
                .get("user/admin")
                .then()
                .extract()
                .body().asString());

        int lastIndex = adminsArray.length() - 1;
        Assert.assertEquals(adminsArray.getJSONObject(lastIndex).getString("id"), testAdmin3.getString("id"));
        Assert.assertEquals(adminsArray.getJSONObject(lastIndex - 1).getString("id"), testAdmin2.getString("id"));
        Assert.assertEquals(adminsArray.getJSONObject(lastIndex - 2).getString("id"), testAdmin1.getString("id"));
    }

    @Test
    public void getAdminTest() {
        JSONObject testUser = addTestAdmin();
        given().contentType(ContentType.JSON)
                .header(new Header("Authorization", "Bearer " + JWT_TOKEN))
                .get("user/admin/" + testUser.getString("id"))
                .then()
                .assertThat()
                .body("login", containsString(testUser.getString("login")))
                .body("firstname", containsString(testUser.getString("firstname")))
                .body("lastname", containsString(testUser.getString("lastname")))
                .body("active", equalTo(true))
                .statusCode(200);
    }

    @Test
    public void updateAdminTest() {
        JSONObject testUser = addTestAdmin();
        JSONObject jsonObj = new JSONObject()
                .put("id", testUser.get("id"))
                .put("login", testUser.get("login"))
                .put("password","zaq1@WSX")
                .put("firstname","UpdatedFirstname")
                .put("lastname","UpdatedLastname");

        given().contentType(ContentType.JSON)
                .body(jsonObj.toString())
                .header(new Header("Authorization", "Bearer " + JWT_TOKEN))
                .put("user/admin")
                .then()
                .assertThat()
                .body("login", containsString(testUser.getString("login")))
                .body("firstname", containsString("UpdatedFirstname"))
                .body("lastname", containsString("UpdatedLastname"))
                .body("active", equalTo(true))
                .statusCode(200);

        jsonObj = new JSONObject()
                .put("id", testUser.get("id"))
                .put("login", "TestAdmin")
                .put("password","zaq1@WSX")
                .put("firstname","UpdatedFirstname")
                .put("lastname","UpdatedLastname");

        given().contentType(ContentType.JSON)
                .body(jsonObj.toString())
                .header(new Header("Authorization", "Bearer " + JWT_TOKEN))
                .put("user/admin")
                .then()
                .assertThat()
                .body("constraint", containsString("User with this login already exists."))
                .statusCode(400);
    }

    @Test
    public void activateAdminTest() {
        JSONObject testUser = addTestAdmin();
        JSONObject jsonObj = new JSONObject()
                .put("id", testUser.get("id"))
                .put("active", false);

        RequestSpecification rs = RestAssured.given();

        rs.contentType(ContentType.JSON)
                .body(jsonObj.toString())
                .header(new Header("Authorization", "Bearer " + JWT_TOKEN))
                .patch("user/admin")
                .then()
                .assertThat()
                .body("login", containsString(testUser.getString("login")))
                .body("firstname", containsString(testUser.getString("firstname")))
                .body("lastname", containsString(testUser.getString("lastname")))
                .body("active", equalTo(false))
                .statusCode(200);
    }

    public JSONObject addTestAdmin() {
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
                        .post("user/admin")
                        .then()
                        .extract()
                        .body()
                        .asString()
        );
    }
}

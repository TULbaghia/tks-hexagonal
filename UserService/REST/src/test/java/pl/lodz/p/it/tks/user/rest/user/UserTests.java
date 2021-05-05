package pl.lodz.p.it.tks.user.rest.user;

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

public class UserTests {

    private String JWT_TOKEN;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://localhost:8181/UserRest/api/";
        RestAssured.port = 8181;
        RestAssured.useRelaxedHTTPSValidation();

        JSONObject jsonObj = new JSONObject()
                .put("login","TestUser")
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
    public void addUserTest() {
        int randomNum = ThreadLocalRandom.current().nextInt(112312, 888888);
        JSONObject jsonObj = new JSONObject()
                .put("login","loginTest" + randomNum)
                .put("password","zaq1@WSX")
                .put("firstname","firstnameTest")
                .put("lastname","lastnameTest")
                .put("userType", "ADMIN");

        given().contentType(ContentType.JSON)
                .body(jsonObj.toString())
                .header(new Header("Authorization", "Bearer " + JWT_TOKEN))
                .post("user")
                .then()
                .assertThat()
                .body("login", containsString("loginTest" + randomNum))
                .body("firstname", containsString("firstnameTest"))
                .body("lastname", containsString("lastnameTest"))
                .body("userType", containsString("ADMIN"))
                .body("active", equalTo(true))
                .statusCode(200);
    }

    @Test
    public void getAllUsersTests() {
        JSONObject testUser1 = addTestUser();
        JSONObject testUser2 = addTestUser();
        JSONObject testUser3 = addTestUser();

        JSONArray usersArray = new JSONArray(given().contentType(ContentType.JSON)
                .header(new Header("Authorization", "Bearer " + JWT_TOKEN))
                .get("user")
                .then()
                .extract()
                .body().asString());

        int lastIndex = usersArray.length() - 1;
        Assert.assertEquals(usersArray.getJSONObject(lastIndex).getString("id"), testUser3.getString("id"));
        Assert.assertEquals(usersArray.getJSONObject(lastIndex - 1).getString("id"), testUser2.getString("id"));
        Assert.assertEquals(usersArray.getJSONObject(lastIndex - 2).getString("id"), testUser1.getString("id"));
    }

    @Test
    public void getUserTest() {
        JSONObject testUser = addTestUser();
        given().contentType(ContentType.JSON)
                .header(new Header("Authorization", "Bearer " + JWT_TOKEN))
                .get("user/" + testUser.getString("id"))
                .then()
                .assertThat()
                .body("login", containsString(testUser.getString("login")))
                .body("firstname", containsString(testUser.getString("firstname")))
                .body("lastname", containsString(testUser.getString("lastname")))
                .body("userType", containsString("ADMIN"))
                .body("active", equalTo(true))
                .statusCode(200);
    }

    @Test
    public void updateUserTest() {
        JSONObject testUser = addTestUser();
        JSONObject jsonObj = new JSONObject()
                .put("id", testUser.get("id"))
                .put("login", testUser.get("login"))
                .put("password","zaq1@WSX")
                .put("firstname","UpdatedFirstname")
                .put("lastname","UpdatedLastname")
                .put("userType", "ADMIN");

        given().contentType(ContentType.JSON)
                .body(jsonObj.toString())
                .header(new Header("Authorization", "Bearer " + JWT_TOKEN))
                .put("user")
                .then()
                .assertThat()
                .body("login", containsString(testUser.getString("login")))
                .body("firstname", containsString("UpdatedFirstname"))
                .body("lastname", containsString("UpdatedLastname"))
                .body("userType", containsString("ADMIN"))
                .body("active", equalTo(true))
                .statusCode(200);

        jsonObj = new JSONObject()
                .put("id", testUser.get("id"))
                .put("login", "TestUser")
                .put("password","zaq1@WSX")
                .put("firstname","UpdatedFirstname")
                .put("lastname","UpdatedLastname")
                .put("userType", "ADMIN");

        given().contentType(ContentType.JSON)
                .body(jsonObj.toString())
                .header(new Header("Authorization", "Bearer " + JWT_TOKEN))
                .put("user")
                .then()
                .assertThat()
                .body("constraint", containsString("User with this login already exists."))
                .statusCode(400);
    }

    @Test
    public void activateUserTest() {
        JSONObject testUser = addTestUser();
        JSONObject jsonObj = new JSONObject()
                .put("id", testUser.get("id"))
                .put("active", false);

        RequestSpecification rs = RestAssured.given();

        rs.contentType(ContentType.JSON)
                .body(jsonObj.toString())
                .header(new Header("Authorization", "Bearer " + JWT_TOKEN))
                .patch("user")
                .then()
                .assertThat()
                .body("login", containsString(testUser.getString("login")))
                .body("firstname", containsString(testUser.getString("firstname")))
                .body("lastname", containsString(testUser.getString("lastname")))
                .body("userType", containsString("ADMIN"))
                .body("active", equalTo(false))
                .statusCode(200);
    }

    public JSONObject addTestUser() {
        int randomNum = ThreadLocalRandom.current().nextInt(112312, 888888);
        JSONObject jsonObj = new JSONObject()
                .put("login", "TestCaseUser" + randomNum)
                .put("password","zaq1@WSX")
                .put("firstname","TestCaseUser")
                .put("lastname","TestCaseUser")
                .put("userType", "ADMIN");

        return new JSONObject(
                given().contentType(ContentType.JSON)
                        .body(jsonObj.toString())
                        .header(new Header("Authorization", "Bearer " + JWT_TOKEN))
                        .post("user")
                        .then()
                        .extract()
                        .body()
                        .asString()
        );
    }
}

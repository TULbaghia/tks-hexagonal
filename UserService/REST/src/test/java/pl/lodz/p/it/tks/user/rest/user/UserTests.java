package pl.lodz.p.it.tks.user.rest.user;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.specification.RequestSpecification;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.images.builder.ImageFromDockerfile;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.nio.file.Path;
import java.util.concurrent.ThreadLocalRandom;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

@SuppressWarnings("rawtypes")
@Testcontainers
public class UserTests {

    private static String JWT_TOKEN;

    @Container
    private static final GenericContainer serviceOne = new GenericContainer<>(
            new ImageFromDockerfile()
                    .withDockerfileFromBuilder(builder
                            -> builder
                            .from("payara/server-full:5.2020.7-jdk11")
                            .copy("UserServiceApp-1.0-SNAPSHOT.war", "/opt/payara/deployments")
                            .copy("RentServiceApp-1.0-SNAPSHOT.war", "/opt/payara/deployments")
                            .build())
                    .withFileFromPath("UserServiceApp-1.0-SNAPSHOT.war",
                            Path.of("target", "../../UserServiceApp/target/UserServiceApp-1.0-SNAPSHOT.war"))
                    .withFileFromPath("RentServiceApp-1.0-SNAPSHOT.war",
                            Path.of("target", "../../../RentService/RentServiceApp/target/RentServiceApp-1.0-SNAPSHOT.war"))
    ).withExposedPorts(8181, 8080, 4848).waitingFor(Wait.forHttp("/UserServiceApp-1.0-SNAPSHOT/api/start").forPort(8080).forStatusCode(200));

    @BeforeAll
    public static void setup() {
        serviceOne.start();
        RestAssured.baseURI = "https://localhost/UserServiceApp-1.0-SNAPSHOT/api/";
        RestAssured.port = serviceOne.getMappedPort(8181);

        JSONObject jsonObj = new JSONObject()
                .put("login", "TestAdmin")
                .put("password", "zaq1@WSX");

        RestAssured.useRelaxedHTTPSValidation();
        JWT_TOKEN = given().contentType(ContentType.JSON)
                .body(jsonObj.toString())
                .post("auth/login")
                .body()
                .asString();
    }

    @Test
    public void addUserFaultTest() {
        JSONObject jsonObj = new JSONObject()
                .put("login","TestowyError")
                .put("password","zaq1@WSX")
                .put("firstname","firstnameTest")
                .put("lastname","lastnameTest")
                .put("userType", "ADMIN");

        RestAssured.baseURI = "https://localhost/RentServiceApp-1.0-SNAPSHOT/api/";
        JSONArray rentServiceUsers = new JSONArray(given().contentType(ContentType.JSON)
                .header(new Header("Authorization", "Bearer " + JWT_TOKEN))
                .get("user/admin")
                .then()
                .extract()
                .body().asString());
        int rentServiceLengthBefore = rentServiceUsers.length();

        RestAssured.baseURI = "https://localhost/UserServiceApp-1.0-SNAPSHOT/api/";
        JSONArray userServiceUsers = new JSONArray(given().contentType(ContentType.JSON)
                .header(new Header("Authorization", "Bearer " + JWT_TOKEN))
                .get("user")
                .then()
                .extract()
                .body().asString());
        int userServiceLengthBefore = userServiceUsers.length();

        given().contentType(ContentType.JSON)
                .body(jsonObj.toString())
                .header(new Header("Authorization", "Bearer " + JWT_TOKEN))
                .post("user")
                .then()
                .assertThat()
                .statusCode(204);

        given().contentType(ContentType.JSON)
                .header(new Header("Authorization", "Bearer " + JWT_TOKEN))
                .get("user/" + jsonObj.getString("login"))
                .then()
                .assertThat()
                .body("constraint", containsString("pl.lodz.p.it.tks.user.repository.exception.RepositoryEntException: Item does not exist."))
                .statusCode(400);

        RestAssured.baseURI = "https://localhost/RentServiceApp-1.0-SNAPSHOT/api/";
        rentServiceUsers = new JSONArray(given().contentType(ContentType.JSON)
                .header(new Header("Authorization", "Bearer " + JWT_TOKEN))
                .get("user/admin")
                .then()
                .extract()
                .body().asString());
        int rentServiceLengthAfter = rentServiceUsers.length();

        RestAssured.baseURI = "https://localhost/UserServiceApp-1.0-SNAPSHOT/api/";
        userServiceUsers = new JSONArray(given().contentType(ContentType.JSON)
                .header(new Header("Authorization", "Bearer " + JWT_TOKEN))
                .get("user")
                .then()
                .extract()
                .body().asString());
        int userServiceLengthAfter = userServiceUsers.length();

        Assertions.assertEquals(rentServiceLengthBefore, rentServiceLengthAfter);
        Assertions.assertEquals(userServiceLengthBefore, userServiceLengthAfter);
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
                .statusCode(204);

        String userUser = given().contentType(ContentType.JSON)
                .header(new Header("Authorization", "Bearer " + JWT_TOKEN))
                .get("user/" + jsonObj.getString("login"))
                .then()
                .assertThat()
                .body("login", containsString(jsonObj.getString("login")))
                .body("firstname", containsString(jsonObj.getString("firstname")))
                .body("lastname", containsString(jsonObj.getString("lastname")))
                .body("userType", containsString("ADMIN"))
                .body("active", equalTo(true))
                .statusCode(200)
                .extract()
                .body()
                .asString();

        JSONObject rentUser = getFromRentService(new JSONObject(userUser).getString("id"), "admin");

        Assertions.assertEquals(rentUser.getString("login"), jsonObj.getString("login"));
        Assertions.assertEquals(rentUser.getString("firstname"), jsonObj.getString("firstname"));
        Assertions.assertEquals(rentUser.getString("lastname"), jsonObj.getString("lastname"));
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
        Assertions.assertEquals(usersArray.getJSONObject(lastIndex).getString("id"), testUser3.getString("id"));
        Assertions.assertEquals(usersArray.getJSONObject(lastIndex - 1).getString("id"), testUser2.getString("id"));
        Assertions.assertEquals(usersArray.getJSONObject(lastIndex - 2).getString("id"), testUser1.getString("id"));

        RestAssured.baseURI = "https://localhost/RentServiceApp-1.0-SNAPSHOT/api/";

        JSONArray usersArrayRent = new JSONArray(given().contentType(ContentType.JSON)
                .header(new Header("Authorization", "Bearer " + JWT_TOKEN))
                .get("user/admin")
                .then()
                .extract()
                .body().asString());

        int lastIndexRent = usersArrayRent.length() - 1;
        Assertions.assertEquals(usersArrayRent.getJSONObject(lastIndexRent).getString("id"), testUser3.getString("id"));
        Assertions.assertEquals(usersArrayRent.getJSONObject(lastIndexRent - 1).getString("id"), testUser2.getString("id"));
        Assertions.assertEquals(usersArrayRent.getJSONObject(lastIndexRent - 2).getString("id"), testUser1.getString("id"));

        RestAssured.baseURI = "https://localhost/UserServiceApp-1.0-SNAPSHOT/api/";
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

        RestAssured.baseURI = "https://localhost/RentServiceApp-1.0-SNAPSHOT/api/";

        given().contentType(ContentType.JSON)
                .header(new Header("Authorization", "Bearer " + JWT_TOKEN))
                .get("user/admin/" + testUser.getString("login"))
                .then()
                .assertThat()
                .body("login", containsString(testUser.getString("login")))
                .body("firstname", containsString(testUser.getString("firstname")))
                .body("lastname", containsString(testUser.getString("lastname")))
                .body("active", equalTo(true))
                .statusCode(200);

        RestAssured.baseURI = "https://localhost/UserServiceApp-1.0-SNAPSHOT/api/";
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

        RestAssured.baseURI = "https://localhost/RentServiceApp-1.0-SNAPSHOT/api/";

        given().contentType(ContentType.JSON)
                .header(new Header("Authorization", "Bearer " + JWT_TOKEN))
                .get("user/admin/" + testUser.get("id"))
                .then()
                .assertThat()
                .body("login", containsString(testUser.getString("login")))
                .body("firstname", containsString("UpdatedFirstname"))
                .body("lastname", containsString("UpdatedLastname"))
                .body("active", equalTo(true))
                .statusCode(200);

        RestAssured.baseURI = "https://localhost/UserServiceApp-1.0-SNAPSHOT/api/";
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

        RestAssured.baseURI = "https://localhost/RentServiceApp-1.0-SNAPSHOT/api/";

        given().contentType(ContentType.JSON)
                .header(new Header("Authorization", "Bearer " + JWT_TOKEN))
                .get("user/admin/" + testUser.get("id"))
                .then()
                .assertThat()
                .body("login", containsString(testUser.getString("login")))
                .body("firstname", containsString(testUser.getString("firstname")))
                .body("lastname", containsString(testUser.getString("lastname")))
                .body("active", equalTo(false))
                .statusCode(200);

        RestAssured.baseURI = "https://localhost/UserServiceApp-1.0-SNAPSHOT/api/";
    }

    public JSONObject getFromRentService(String id, String type) {
        RestAssured.baseURI = "https://localhost/RentServiceApp-1.0-SNAPSHOT/api/";

        String obj = given().contentType(ContentType.JSON)
                .header(new Header("Authorization", "Bearer " + JWT_TOKEN))
                .get("user/" + type + "/" + id)
                .then()
                .extract()
                .body()
                .asString();

        RestAssured.baseURI = "https://localhost/UserServiceApp-1.0-SNAPSHOT/api/";
        return new JSONObject(obj);
    }

    public JSONObject addTestUser() {
        int randomNum = ThreadLocalRandom.current().nextInt(112312, 888888);
        JSONObject jsonObj = new JSONObject()
                .put("login", "TestCaseUser" + randomNum)
                .put("password","zaq1@WSX")
                .put("firstname","TestCaseUser")
                .put("lastname","TestCaseUser")
                .put("userType", "ADMIN");

        given().contentType(ContentType.JSON)
                .body(jsonObj.toString())
                .header(new Header("Authorization", "Bearer " + JWT_TOKEN))
                .post("user")
                .then()
                .extract()
                .body()
                .asString();

        return new JSONObject(
                given().contentType(ContentType.JSON)
                        .header(new Header("Authorization", "Bearer " + JWT_TOKEN))
                        .get("user/" + jsonObj.getString("login"))
                        .then()
                        .extract()
                        .body()
                        .asString()
        );
    }
}

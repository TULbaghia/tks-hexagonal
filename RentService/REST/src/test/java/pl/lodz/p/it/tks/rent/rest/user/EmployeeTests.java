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
public class EmployeeTests extends AbstractContainer {

    private static String JWT_TOKEN;

    @BeforeAll
    public static void setup() {
        getService();
        RestAssured.baseURI = "https://localhost/UserServiceApp-1.0-SNAPSHOT/api/";
        RestAssured.port = serviceOne.getMappedPort(8181);
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

        RestAssured.baseURI = "https://localhost/RentServiceApp-1.0-SNAPSHOT/api/";

        JWT_TOKEN = r.getBody().asString();
    }

//    @Test
//    public void addEmployeeTest() {
//        int randomNum = ThreadLocalRandom.current().nextInt(112312, 888888);
//        JSONObject jsonObj = new JSONObject()
//                .put("login","AddEmployeeTest" + randomNum)
//                .put("firstname","AddEmployeeTest")
//                .put("lastname","AddEmployeeTest");
//
//        JSONObject userServiceObj = new JSONObject(jsonObj.toString()).put("userType", "EMPLOYEE").put("password", "zaq1@WSX");
//
//        RestAssured.baseURI = "https://localhost/UserServiceApp-1.0-SNAPSHOT/api/";
//        given().contentType(ContentType.JSON)
//                .body(userServiceObj.toString())
//                .header(new Header("Authorization", "Bearer " + JWT_TOKEN))
//                .post("user")
//                .then()
//                .assertThat()
//                .body("login", containsString("AddEmployeeTest" + randomNum))
//                .body("firstname", containsString("AddEmployeeTest"))
//                .body("lastname", containsString("AddEmployeeTest"))
//                .body("active", equalTo(true))
//                .statusCode(200);
//
//        RestAssured.baseURI = "https://localhost/RentServiceApp-1.0-SNAPSHOT/api/";
//        given().contentType(ContentType.JSON)
//                .body(jsonObj.toString())
//                .header(new Header("Authorization", "Bearer " + JWT_TOKEN))
//                .post("user/employee")
//                .then()
//                .assertThat()
//                .body("login", containsString("AddEmployeeTest" + randomNum))
//                .body("firstname", containsString("AddEmployeeTest"))
//                .body("lastname", containsString("AddEmployeeTest"))
//                .body("active", equalTo(true))
//                .statusCode(200);
//    }

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
        Assertions.assertEquals(employeesArray.getJSONObject(lastIndex).getString("id"), testEmployee3.getString("id"));
        Assertions.assertEquals(employeesArray.getJSONObject(lastIndex - 1).getString("id"), testEmployee2.getString("id"));
        Assertions.assertEquals(employeesArray.getJSONObject(lastIndex - 2).getString("id"), testEmployee1.getString("id"));
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

//    @Test
//    public void updateEmployeeTest() {
//        JSONObject testUser = addTestEmployee();
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
//                .put("user/employee")
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
//                .put("login", "TestEmployee")
//                .put("password","zaq1@WSX")
//                .put("firstname","UpdatedFirstname")
//                .put("lastname","UpdatedLastname");
//
//        given().contentType(ContentType.JSON)
//                .body(jsonObj.toString())
//                .header(new Header("Authorization", "Bearer " + JWT_TOKEN))
//                .put("user/employee")
//                .then()
//                .assertThat()
//                .body("constraint", containsString("User with this login already exists."))
//                .statusCode(400);
//    }

//    @Test
//    public void activateEmployeeTest() {
//        JSONObject testUser = addTestEmployee();
//        JSONObject jsonObj = new JSONObject()
//                .put("id", testUser.get("id"))
//                .put("active", false);
//
//        RequestSpecification rs = RestAssured.given();
//
//        rs.contentType(ContentType.JSON)
//                .body(jsonObj.toString())
//                .header(new Header("Authorization", "Bearer " + JWT_TOKEN))
//                .patch("user/employee")
//                .then()
//                .assertThat()
//                .body("login", containsString(testUser.getString("login")))
//                .body("firstname", containsString(testUser.getString("firstname")))
//                .body("lastname", containsString(testUser.getString("lastname")))
//                .body("active", equalTo(false))
//                .statusCode(200);
//    }

    public JSONObject addTestEmployee() {
        int randomNum = ThreadLocalRandom.current().nextInt(112312, 888888);
        JSONObject jsonObj = new JSONObject()
                .put("login","TestCaseEmployee" + randomNum)
                .put("firstname","TestCaseEmployee")
                .put("lastname","TestCaseEmployee");

        JSONObject userServiceObj = new JSONObject(jsonObj.toString()).put("userType", "EMPLOYEE").put("password", "zaq1@WSX");

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

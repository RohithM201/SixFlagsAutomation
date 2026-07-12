package APITests;
import Libraries.UI;
import io.restassured.RestAssured;
import org.testng.annotations.Test;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


    public class GETParkAPI implements UI {

        @Test
        public void  positiveGetParkID () throws IOException {

            RestAssured.baseURI = link;


            String response =
                    given()
                            .log().all()
                            .when()
                            .get("/api/v1/parks/1")
                            .then()
                            .statusCode(200)
                            .body("id", equalTo(1))
                            .body("name", equalTo("Six Flags Magic Mountain"))
                            .body("status", equalTo("Open"))
                            .extract()
                            .asString();

            System.out.println(response);


        }
@Test
public void negativeGetParkID () throws IOException
{
    RestAssured.baseURI = link;
    String response = given().log().all().get("/api/v1/park/999999").then().statusCode(404).extract().response().asString();

    System.out.println(response);
}}




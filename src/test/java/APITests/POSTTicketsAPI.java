package APITests;

import Libraries.UI;
import io.restassured.RestAssured;
import org.testng.annotations.Test;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class POSTTicketsAPI implements UI {
    @Test
    public void postUser () throws IOException {
        RestAssured.baseURI = link;

        String requestBody = """
                    {
                    "firstName": "Rohith",
                    "lastName": "Mohan",
                    "email": "rohithmohan201@gmail.com",
                    "parkId": 1,
                    "ticketType": "General 18-64",
                    "visitDate": "2026-07-15"
                }
                """;

        String response =
                given()
                        .log().all()
                        .contentType("application/json")
                        .header("Accept", "application/json")
                        .body(requestBody)
                        .when()
                        .post("/api/v1/tickets")
                        .then()
                        .statusCode(201)
                        .body("firstName", equalTo("Rohith"))
                        .body("lastName", equalTo("Mohan"))
                        .body("email", equalTo("rohithmohan201@gmail.com"))
                        .body("parkId", equalTo(1))
                        .body("ticketType", equalTo("General 18-64"))
                        .body("visitDate", equalTo("2026-07-15")).extract().asString();
    }


        @Test
        public void negativeGetParkID_MissingEmail () throws IOException {

//Note: This is actually a post request
            String invalidrequestBody = """
                        "firstName": "Rohith",
                        "lastName": "Mohan",
                        "parkId": 1, 
                        "ticketType": "General 18-64", 
                        "visitDate": "2026-07-15" 
                        }""";

                RestAssured.baseURI = link;
        String Response =
                given()
                        .contentType("application/json")
                        .header("Accept", "application/json")
                        .body(invalidrequestBody)
                        .when()
                        .post("/api/v1/tickets")
                        .then()
                        .statusCode(400).toString();
    }
}












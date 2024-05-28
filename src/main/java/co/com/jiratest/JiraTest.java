package co.com.jiratest;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;

import java.io.File;

import static io.restassured.RestAssured.given;

public class JiraTest {

    public static void main(String[] args) {

        RestAssured.baseURI = "http://localhost:8080";
        SessionFilter session = new SessionFilter();

        //authentication
        String response = given().log().all().header("Content-Type", "application/json")
                        .body("{ \"username\": \"Arevalocris\", \"password\": \"123456\" }")
                .when().filter(session).post("/rest/auth/1/session")
                        .then().log().all().extract().response().asString();




        //AddCommnet into bug in Jira
        given().log().all().pathParam("id","10005")
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"body\": \"this is my third comment\",\n" +
                        "    \"visibility\": {\n" +
                        "        \"type\": \"role\",\n" +
                        "        \"value\": \"Administrators\"\n" +
                        "    }\n" +
                        "}").filter(session).when().post("/rest/api/2/issue/{id}/comment")
                .then().log().all().assertThat().statusCode(201);


        //add attachment
        given().header("X-Atlassian-Token", "no-check").filter(session).pathParam("key", "10005")
                .header("Content-Type", "multipart/form-data")
                .multiPart("file", new File("src/main/resources/jira.txt"))
                .when().post("/rest/api/2/issue/{key}/attachments")
                .then().log().all().assertThat().statusCode(200);
    }
}

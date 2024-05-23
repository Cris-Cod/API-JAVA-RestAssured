package co.com;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class Prueba {
    public static void main(String[] args) {


        String response = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "    \"email\": \"eve.holt@reqres.in\",\n" +
                        "    \"password\": \"cityslicka\"\n" +
                        "}")
                .post("https://reqres.in/api/login")
                .then().log().all()
                .extract()
                .asString();

        System.out.println(response);
    }
}

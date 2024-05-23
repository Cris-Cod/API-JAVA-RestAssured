package co.com;

import co.com.files.Payload;
import co.com.files.ReUsableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class Basics {
    public static void main(String[] args) {
        //validate if add Place API is working as expected
        //given - all input details
        // when - Submit the API
        //then  validaste de responde
        RestAssured.baseURI = "https://rahulshettyacademy.com";
        String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json").
                body(Payload.AddPlace()).when().post("/maps/api/place/add/json").
                then().assertThat().statusCode(200).body("scope", equalTo("APP"))
                        .extract().response().asString();

        System.out.println(response);
        JsonPath js = ReUsableMethods.rawToJson(response);  // for parsing Json
        String placeId = js.getString("place_id");

        System.out.println(placeId);

                //Add place -> Update place with New Address - > get place to validate if New Address is present in  response

        //Update place
        String newAddress = "Summer Walk, Africa";
        given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
                .body("{\n" +
                        "\"place_id\":\""+placeId+"\",\n" +
                        "\"address\":\""+newAddress+"\",\n" +
                        "\"key\":\"qaclick123\"\n" +
                        "}")
                .when().put("/maps/api/place/update/json")
                .then().assertThat().log().all().statusCode(200).body("msg",equalTo("Address successfully updated"));


        //Get place

        String getPlaceResponse = given().log().all().queryParam("key", "qaclick123")
                .queryParam("place_id", placeId)
                .when().get("/maps/api/place/get/json")
                .then().assertThat().log().all().statusCode(200)
                .extract().response().asString();

        JsonPath js1 = ReUsableMethods.rawToJson(getPlaceResponse);
        String actualAddress = js1.getString("address");
        System.out.println(actualAddress);
        Assert.assertEquals(actualAddress, newAddress, "the address in correct");

    }
}

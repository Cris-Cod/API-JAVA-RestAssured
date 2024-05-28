package co.com.libraryapi;

import co.com.files.Payload;
import co.com.files.ReUsableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class DynamicJson {

     @Test(dataProvider = "BooksData")
    public void addBook(String isbn, String aisle){
         RestAssured.baseURI = "http://216.10.245.166";
         String response = given().log().all().header("Content-Type", "application/json")
                 .body(Payload.AddBook(isbn,aisle))
                 .when()
                 .post("/Library/Addbook.php")
                 .then().log().all().assertThat().statusCode(200)
                 .extract().response().asString();

        JsonPath js = ReUsableMethods.rawToJson(response);
        String id = js.get("ID");
        System.out.println(id);
     }

     @DataProvider(name = "BooksData")
     public Object[][] getData(){
        return new Object[][] {{"gtyy","9369"},{"rtyu", "0334"}, {"bnmj", "0325"}};
     }
}

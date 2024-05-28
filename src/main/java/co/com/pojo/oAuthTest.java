package co.com.pojo;

import io.restassured.path.json.JsonPath;

import java.util.List;

import static io.restassured.RestAssured.given;

public class oAuthTest {
    public static void main(String[] args) {

        String response = given().formParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                .formParams("client_secret","erZOWM9g3UtwNRj340YYaK_W")
                .formParams("grant_type", "client_credentials")
                .formParams("scope", "trust")
                .when().log().all()
                .post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token")
                .asString();

        System.out.println(response);
        JsonPath js = new JsonPath(response);
        String accessToken = js.getString("access_token");

        GetCourse gc = given().log().all()
                .queryParams("access_token", accessToken)
                .when().log().all()
                .get("https://rahulshettyacademy.com/oauthapi/getCourseDetails").as(GetCourse.class);

        System.out.println(gc.getLinkedIn());
        System.out.println(gc.getInstructor());
        System.out.println(gc.getCourses().getApi().get(1).getCourseTitle());
        //System.out.println(gc.getCourses().getApi().get(1).getPrice());

        List<Api> apiCourses = gc.getCourses().getApi();
        for (int i = 0; i < apiCourses.size(); i++) {
           if (apiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")){
               System.out.println(apiCourses.get(i).getPrice());
           }
        }

        for (int i = 0; i < apiCourses.size(); i++) {
            System.out.println(apiCourses.get(i).getCourseTitle());
        }

    }
}

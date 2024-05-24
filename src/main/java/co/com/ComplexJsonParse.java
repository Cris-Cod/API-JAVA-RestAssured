package co.com;

import co.com.files.Payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

    public static void main(String[] args) {
        JsonPath js = new JsonPath(Payload.CoursePric());

       //Prient of numbres of courses
       int count = js.getInt("courses.size()");
       System.out.println(count);

       //Print purchase Amount
        int totalAmount = js.getInt("dashboard.purchaseAmount");
        System.out.println(totalAmount);

        //Print Title of first course
        String titleFirstCourse =  js.get("courses[0].title");
        System.out.println(titleFirstCourse);

        //Print All courses titles and their respective prices
        for (int i = 0; i < count; i++) {
            String couseTitles = js.get("courses["+i+"].title");
            System.out.println(couseTitles);
            System.out.println(js.get("courses["+i+"].price").toString());
        }

        System.out.println("Print No of copies sold by RPA Course");
        for (int i = 0; i < count; i++) {
            String couseTitles = js.get("courses["+i+"].title");
            if(couseTitles.equalsIgnoreCase("RPA")){
                //copies sold
               int copies = js.get("courses["+i+"].copies");
                System.out.println(copies);
                break;
            }
        }

    }


}

package co.com;

import co.com.files.Payload;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SumValidation {

    @Test
    public void sumofCourses(){
        int sum = 0;
        JsonPath js = new JsonPath(Payload.CoursePric());
        int count = js.getInt("courses.size()");
        for (int i = 0; i < count; i++) {
            int price =js.get("courses["+i+"].price");
            int copies =js.get("courses["+i+"].copies");
            int amount = price * copies;
            System.out.println(amount);
            sum += amount;

        }
        System.out.println("sum all courses " + sum);
        int purchaseAmount = js.getInt("dashboard.purchaseAmount");
        Assert.assertEquals(sum,purchaseAmount);
    }
}

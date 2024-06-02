package co.com.e2e.ecommerce;

import co.com.e2e.pojo.LoginRequest;
import co.com.e2e.pojo.LoginResponse;
import co.com.e2e.pojo.OrderDetail;
import co.com.e2e.pojo.Orders;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class EcommerceApiTest {

    public static void main(String[] args) {
     RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
             .setContentType(ContentType.JSON).build();
     LoginRequest loginRequest = new LoginRequest();
     loginRequest.setUserEmail("postcris@gmail.com");
     loginRequest.setUserPassword("Postman123#");


     RequestSpecification reqLogin = given().log().all().spec(req).body(loginRequest);
     LoginResponse loginResponse = reqLogin.when().post("/api/ecom/auth/login").then().extract().response().as(LoginResponse.class);
     System.out.println(loginResponse.getToken());
     System.out.println(loginResponse.getUserId());
     System.out.println(loginResponse.getMessage());
     String token = loginResponse.getToken();
     String id = loginResponse.getUserId();


     //Add product
     RequestSpecification addProductBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
             .addHeader("Authorization", token).build();

     RequestSpecification reqAddProduct = given().log().all().spec(addProductBaseReq).param("productName", "Laptop")
             .param("productAddedBy", id)
             .param("productCategory", "tecnology")
             .param("productSubCategory", "gamer")
             .param("productPrice", "1500")
             .param("productDescription", "Msi0089")
             .param("productFor", "all")
             .multiPart("productImage", new File("img/laptop.jpg"));

     String addProductResponse = reqAddProduct.when().post("/api/ecom/product/add-product")
             .then().log().all().extract().response().asString();

     JsonPath js = new JsonPath(addProductResponse);
     String productid = js.get("productId");
     String messageAdd = js.get("message");
     System.out.println(messageAdd);


     //Create Order
     RequestSpecification createOrderBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
             .addHeader("Authorization", token).setContentType(ContentType.JSON).build();

     OrderDetail orderDetail = new OrderDetail();
     orderDetail.setCountry("Antarctica");
     orderDetail.setProductOrderedId(productid);

     List<OrderDetail> orderDetailist = new ArrayList<OrderDetail>();
     orderDetailist.add(orderDetail);

     Orders orders = new Orders();
     orders.setOrders(orderDetailist);

     RequestSpecification createOrderReq = given().log().all().spec(createOrderBaseReq).body(orders);

     String resposeAddOrder = createOrderReq.when().post("/api/ecom/order/create-order").then().log().all().extract().response().asString();
     System.out.println(resposeAddOrder);

     JsonPath js3 = new JsonPath(resposeAddOrder);
     String orderId = js3.get("orders[0]");
     System.out.println(orderId);

     //Delete Product

     RequestSpecification deleteProductBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
             .addHeader("Authorization", token).build();

     RequestSpecification deleteProductreq = given().log().all().spec(deleteProductBaseReq).pathParam("productId", productid);
     String reponseDeleteProduct = deleteProductreq.when().delete("/api/ecom/product/delete-product/{productId}").then().log().all().
             extract().response().asString();

     System.out.println(reponseDeleteProduct);

     JsonPath js1 = new JsonPath(reponseDeleteProduct);

     Assert.assertEquals("Product Deleted Successfully", js1.get("message"));


     //Delete order
     RequestSpecification deleteOrderBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
             .addHeader("Authorization", token).build();

     RequestSpecification deleteOrderReq = given().log().all().spec(deleteOrderBaseReq).pathParam("orderId", orderId);
     String responseDeleteOrder = deleteOrderReq.when().delete("/api/ecom/order/delete-order/{orderId}").then().log().all()
             .extract().response().asString();

     System.out.println(responseDeleteOrder);


    }

}

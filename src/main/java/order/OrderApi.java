package order;

import client.ScooterRentSpec;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderApi extends ScooterRentSpec {
    public static final String API_ORDERS = "/api/v1/orders";

    public ValidatableResponse createOrder(Order order) {
        return given()
                .spec(ScooterRentSpec.requestSpecification())
                .and()
                .body(order)
                .when()
                .post(API_ORDERS)
                .then();

    }
}

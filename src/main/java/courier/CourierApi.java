package courier;

import client.ScooterRentSpec;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CourierApi extends ScooterRentSpec {
    private static final String API_CREATE_COURIER = "/api/v1/courier";
    private static final String API_LOGIN = "/api/v1/courier/login";
    private static final String API_DELETE = "/api/v1/courier/";

    public ValidatableResponse courierReg(Courier courier) {
        return given()
                .spec(ScooterRentSpec.requestSpecification())
                .and()
                .body(courier)
                .when()
                .post(API_CREATE_COURIER)
                .then();
    }

    public ValidatableResponse courierLogin(Courier courier) {
        return given()
                .spec(ScooterRentSpec.requestSpecification())
                .and()
                .body(courier)
                .when()
                .post(API_LOGIN)
                .then();
    }

    public ValidatableResponse courierDelete(String id) {
        return given()
                .spec(ScooterRentSpec.requestSpecification())
                .delete(API_DELETE + id)
                .then();
    }

    //сделала дополнительную проверку, проверяющую, что курьер удален и возникнет ошибка, если обратиться к его id
    public ValidatableResponse checkCourierDeleted(String id) {
        return given()
                .spec(ScooterRentSpec.requestSpecification())
                .when()
                .get("/api/v1/courier/" + id + "/ordersCount")
                .then()
                .statusCode(404);
    }
}

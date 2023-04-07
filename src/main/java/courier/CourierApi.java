package courier;

import client.ScooterRentSpec;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CourierApi extends ScooterRentSpec {
    public static final String API_CREATE_COURIER = "/api/v1/courier";
    public static final String API_LOGIN = "/api/v1/courier/login";
    public static final String API_DELETE = "/api/v1/courier/";

    public ValidatableResponse CourierReg(Courier courier) {
        return given()
                .spec(ScooterRentSpec.requestSpecification())
                .and()
                .body(courier)
                .when()
                .post(API_CREATE_COURIER)
                .then();
    }

    public ValidatableResponse CourierLogin(Courier courier) {
        return given()
                .spec(ScooterRentSpec.requestSpecification())
                .and()
                .body(courier)
                .when()
                .post(API_LOGIN)
                .then();
    }

    public Response CourierDelete(Integer id) {
        return given()
                .spec(ScooterRentSpec.requestSpecification())
                .delete(API_DELETE + id);
    }

    //сделала дополнительную проверку, проверяющую, что курьер удален и возникнет ошибка, если обратиться к его id
    public ValidatableResponse checkCourierDeleted(Integer id) {
        return given()
                .spec(ScooterRentSpec.requestSpecification())
                .when()
                .get("/api/v1/courier/" + id + "/ordersCount")
                .then()
                .statusCode(404);
    }
}

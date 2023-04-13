package apitests;

import client.ScooterRentSpec;
import courier.CourierApi;
import courier.CourierData;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.is;

public class DeleteCourierTest {
    static String id;
    CourierData courierData = new CourierData();
    CourierApi courierApi = new CourierApi();

    @Before
    public void setUp() {
        RestAssured.baseURI = ScooterRentSpec.BASE_URI;
    }

    @After
    public void tearDown() {
        courierApi.courierDelete(id);
        courierApi.checkCourierDeleted(id);
    }

    @Test
    @DisplayName("Курьера можно удалить")
    @Description("Проверка, что созданный курьер успешно удаляется")
    public void deleteCourierTest() {
        ValidatableResponse response = courierApi.courierReg(CourierData.getCourierNew());
        response.assertThat().body("ok", is(true)).and().statusCode(SC_CREATED);

        ValidatableResponse responseLogin = courierApi.courierLogin(CourierData.getCourierNew());
        id = responseLogin.extract().path("id").toString();
        ValidatableResponse deleteCourier = courierApi.courierDelete(id);
        deleteCourier.statusCode(SC_OK).and().assertThat().body("ok", is(true));
    }
}
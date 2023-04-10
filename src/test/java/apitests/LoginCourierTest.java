package apitests;

import client.ScooterRentSpec;
import courier.Courier;
import courier.CourierApi;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class LoginCourierTest {
    static String id;
    Courier courier = new Courier("Petrovich1", "1234", "Petr");
    CourierApi courierApi = new CourierApi();

    @Before
    public void setUp() {
        RestAssured.baseURI = ScooterRentSpec.BASE_URI;
    }

    @Test
    @DisplayName("Авторизация курьера без логина")
    @Description("Проверка, что курьер не может авторизоваться без логина")
    public void NoLoginCourierWithoutLoginTest() {
        Courier courier = new Courier("Petrovich1", "", "Petr");
        ValidatableResponse response = courierApi.courierLogin(courier);
        response.assertThat().statusCode(SC_BAD_REQUEST).and().body("message", is("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Авторизация курьера")
    @Description("Проверка, что курьер может авторизоваться с валидными значениями")
    public void SuccessLoginCourierTest() {
        ValidatableResponse response = courierApi.courierReg(courier);
        ValidatableResponse loginResponse = courierApi.courierLogin(courier);
        id = loginResponse.extract().path("id").toString();
        loginResponse.assertThat().statusCode(SC_OK).body("id", notNullValue());
    }

    @Test
    @DisplayName("Авторизация курьера без пароля")
    @Description("Проверка, что курьер не может авторизоваться пароля")
    public void NoLoginCourierWithoutPasswordTest() {
        Courier courier = new Courier("Petrovich1", "", "Petr");
        ValidatableResponse response = courierApi.courierLogin(courier);
        response.assertThat().statusCode(SC_BAD_REQUEST).and().body("message", is("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Авторизация курьера с неправильным паролем")
    @Description("Проверка, что курьер не может авторизоваться с неправильным паролем")
    public void NoLoginCourierWithWrongPasswordTest() {
        Courier courier = new Courier("Petrovich1", "1234567");
        ValidatableResponse response = courierApi.courierLogin(courier);
        response.assertThat().statusCode(SC_NOT_FOUND).and().body("message", is("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Авторизация курьера с неправильным логином")
    @Description("Проверка, что курьер не может авторизоваться с логином паролем")
    public void NoLoginCourierWithWrongLoginTest() {
        Courier courier = new Courier("Petrovich123", "1234");
        ValidatableResponse response = courierApi.courierLogin(courier);
        response.assertThat().statusCode(SC_NOT_FOUND).and().body("message", is("Учетная запись не найдена"));
    }

    @After
    public void tearDown() {
        courierApi.courierDelete(id);
        courierApi.checkCourierDeleted(id);
    }
}

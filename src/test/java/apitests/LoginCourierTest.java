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

public class LoginCourierTest {
    static Integer id;
    Courier courier = new Courier("Petrovich1", "1234", "Petr");
    CourierApi courierApi = new CourierApi();

    @Before
    public void setUp() {
        RestAssured.baseURI = ScooterRentSpec.BASE_URI;
    }

    @Test
    @DisplayName("Авторизация курьера")
    @Description("Проверка, что курьер может авторизоваться с валидными значениями")
    public void SuccessLoginCourierTest() {
        Courier courier = new Courier("Petrovich1", "1234", "Petr");
        ValidatableResponse response = courierApi.CourierReg(courier);
        ValidatableResponse responseLogin = courierApi.CourierLogin(courier);
        id = responseLogin.extract().path("id");
        responseLogin.assertThat().statusCode(SC_OK).body("id", is(id));
    }

    @Test
    @DisplayName("Авторизация курьера без логина")
    @Description("Проверка, что курьер не может авторизоваться без логина")
    public void NoLoginCourierWithoutLoginTest() {
        Courier courier = new Courier("", "1234", "Petr");
        ValidatableResponse response = courierApi.CourierLogin(courier);
        response.assertThat().statusCode(SC_BAD_REQUEST).and().body("message", is("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Авторизация курьера без пароля")
    @Description("Проверка, что курьер не может авторизоваться пароля")
    public void NoLoginCourierWithoutPasswordTest() {
        Courier courier = new Courier("Petrovich1", "", "Petr");
        ValidatableResponse response = courierApi.CourierLogin(courier);
        response.assertThat().statusCode(SC_BAD_REQUEST).and().body("message", is("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Авторизация курьера с неправильным паролем")
    @Description("Проверка, что курьер не может авторизоваться с неправильным паролем")
    public void NoLoginCourierWithWrongPasswordTest() {
        Courier courier = new Courier("Petrovich1", "1234567");
        ValidatableResponse response = courierApi.CourierLogin(courier);
        response.assertThat().statusCode(SC_NOT_FOUND).and().body("message", is("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Авторизация курьера с неправильным логином")
    @Description("Проверка, что курьер не может авторизоваться с логином паролем")
    public void NoLoginCourierWithWrongLoginTest() {
        Courier courier = new Courier("Petrovich123", "1234");
        ValidatableResponse response = courierApi.CourierLogin(courier);
        response.assertThat().statusCode(SC_NOT_FOUND).and().body("message", is("Учетная запись не найдена"));
    }

    @After
    public void tearDown() {
        courierApi.CourierDelete(id);
        courierApi.checkCourierDeleted(id);
    }
}

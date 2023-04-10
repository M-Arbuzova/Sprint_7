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

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.is;

public class CreateCourierTest {

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
    @DisplayName("Регистрации нового курьера")
    @Description("Проверка, что можно создать нового курьера с валидными значениями")
    public void RegNewCourierTest() {
        ValidatableResponse response = courierApi.courierReg(CourierData.getCourierNew());
        response.assertThat().statusCode(SC_CREATED).body("ok", is(true)).log().all();
        ValidatableResponse loginResponse = courierApi.courierLogin(CourierData.getCourierNew());
        id = loginResponse.extract().path("id").toString();
    }

    @Test
    @DisplayName("Нельзя зарегистрироваться двух одинаковых курьеров")
    @Description("Проверка, что нельзя создать нового курьера, если вводимый логин уже есть в системе")
    public void RegDuplicateCourierTest() {
        ValidatableResponse response = courierApi.courierReg(CourierData.getCourierNew());
        response.statusCode(SC_CREATED);
        ValidatableResponse loginResponse = courierApi.courierLogin(CourierData.getCourierNew());
        id = loginResponse.extract().path("id").toString();
        ValidatableResponse response2 = courierApi.courierReg(CourierData.getCourierNew());
        response2.statusCode(SC_CONFLICT)
                .and().assertThat().body("message", is("Этот логин уже используется. Попробуйте другой."));

    }

    @Test
    @DisplayName("Нельзя зарегистрировать курьера без логина")
    @Description("Проверка, что появится ошибка при попытке создания курьера без заполнения логина")
    public void RegCourierWithoutLoginTest() {
        ValidatableResponse response = courierApi.courierReg(CourierData.getCourierWithoutLogin());
        response.statusCode(SC_BAD_REQUEST)
                .and().assertThat().body("message", is("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Нельзя зарегистрировать курьера без пароля")
    @Description("Проверка, что появится ошибка при попытке создания курьера без заполнения пароля")
    public void RegCourierWithoutPasswordTest() {
        ValidatableResponse response = courierApi.courierReg(CourierData.getCourierWithoutPassword());
        response.statusCode(SC_BAD_REQUEST)
                .and().assertThat().body("message", is("Недостаточно данных для создания учетной записи"));
    }
}


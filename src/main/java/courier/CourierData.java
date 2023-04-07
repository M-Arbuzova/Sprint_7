package courier;


import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

public class CourierData {

    private static final Courier courierNew = new Courier(randomAlphanumeric(5), "1234", "saske");
    private static final Courier courierYet = new Courier("ninja", "1234", "saske");
    private static final Courier courierWithoutLogin = new Courier("", "1234", "saske");
    private static final Courier courierWithoutPassword = new Courier("Petr", "", "saske");

    public static Courier getCourierNew() {
        return courierNew;
    }

    public static Courier getCourierYet() {
        return courierYet;
    }

    public static Courier getCourierWithoutLogin() {
        return courierWithoutLogin;
    }

    public static Courier getCourierWithoutPassword() {
        return courierWithoutPassword;
    }
}

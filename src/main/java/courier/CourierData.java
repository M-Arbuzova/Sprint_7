package courier;


import org.apache.commons.lang3.RandomStringUtils;

public class CourierData {

    private static final Courier courierNew = new Courier(RandomStringUtils.randomAlphabetic(5), "1234", "saske");
    private static final Courier courierDefault = new Courier("ninja", "1234", "saske");
    private static final Courier courierWithoutLogin = new Courier("", "1234", "saske");
    private static final Courier courierWithoutPassword = new Courier("Petr", "", "saske");

    public static Courier getCourierNew() {
        return courierNew;
    }

    public static Courier courierDefault() {
        return courierDefault;
    }

    public static Courier getCourierWithoutLogin() {
        return courierWithoutLogin;
    }

    public static Courier getCourierWithoutPassword() {
        return courierWithoutPassword;
    }
}


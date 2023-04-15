package courier;


import org.apache.commons.lang3.RandomStringUtils;

public class CourierData {

    private static final Courier courierNew = new Courier(RandomStringUtils.randomAlphabetic(5), "1234", "saske");
    private static final Courier courierDefault = new Courier("ninja", "1234", "saske");
    private static final Courier courierWithoutLogin = new Courier("", RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(5));

    public static Courier getCourierNew() {
        return courierNew;
    }

    public static Courier getCourierDefault() {
        return courierDefault;
    }

    public static Courier getCourierWithoutLogin() {
        return courierWithoutLogin;
    }

}


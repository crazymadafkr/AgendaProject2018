package enumsandinterfaces;

/**
 * Created by andre on 6/23/2018.
 */

public enum OrderType {
    FIRSTNAME("First name"),
    LASTNAME("Last name"),
    BIRTHDATE("Date of birth"),
    TELEPHONE("Telephone number");

    private String message;

    OrderType(String message) {
        this.message = message;
    }

    public static String enumToText(OrderType OrderType) {
        return OrderType.getMessage();
    }

    public static OrderType fromString(String message) {
        for (OrderType OrderType : OrderType.values()) {
            if (OrderType.message.equalsIgnoreCase(message)) {
                return OrderType;
            }
        }
        return FIRSTNAME;
    }

    public String getMessage() {
        return message;
    }
}

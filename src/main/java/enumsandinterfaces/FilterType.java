package enumsandinterfaces;

/**
 * Created by andre on 6/24/2018.
 */

public enum FilterType {
    ALL("Show all contacts"),
    BORN_CURRENT_MONTH("Born this month"),
    BORN_TODAY("Born today"),
    MOBILE_TELEPHONE("Has a mobile phone number"),
    LANDLINE_TELEPHONE("Has a landline phone number"),
    CUSTOM("Custom filter");

    private String message;

    FilterType(String message) {
        this.message = message;
    }

    public static String enumToText(FilterType FilterType) {
        return FilterType.getMessage();
    }

    public static FilterType fromString(String message) {
        for (FilterType FilterType : FilterType.values()) {
            if (FilterType.message.equalsIgnoreCase(message)) {
                return FilterType;
            }
        }
        return ALL;
    }

    public String getMessage() {
        return message;
    }

}

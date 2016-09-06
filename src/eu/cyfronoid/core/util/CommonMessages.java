package eu.cyfronoid.core.util;

public enum CommonMessages {
    INVALID_ARGUMENT("Invalid argument value was passed to method %s."),
    NULL_REFERENCE("Error occured. Trying to set attribute %s with null value."),
    ;

    private final String msg;

    CommonMessages(String msg) {
        this.msg = msg;
    }

    public String get(Object... additionalInfo) {
        return String.format(msg, additionalInfo);
    }
}

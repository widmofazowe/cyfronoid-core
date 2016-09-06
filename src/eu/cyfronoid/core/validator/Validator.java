package eu.cyfronoid.core.validator;

public class Validator {

    public static boolean isAlphanumeric(String stringToCheck) {
        return stringToCheck.matches("[[:alnum:]]");
    }

    public static boolean between(long number, long min, long max) {
        return number > min && number < max;
    }

    public static boolean betweenOrEqual(long number, long min, long max) {
        return number >= min && number <= max;
    }
}

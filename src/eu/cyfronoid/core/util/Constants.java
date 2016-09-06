package eu.cyfronoid.core.util;

public enum Constants {
    EMPTY(""),
    SPACEBAR(" "),
    TAB("    "),
    NEW_LINE(System.getProperty("line.separator")),
    ;

    public final String value;

    Constants(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}



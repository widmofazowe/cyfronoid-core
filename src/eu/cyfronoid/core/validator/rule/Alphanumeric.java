package eu.cyfronoid.core.validator.rule;

public class Alphanumeric extends RegexRule {
    private static final String pattern = "[[:alnum:]]";

    public Alphanumeric() {
        super(pattern);
    }
}

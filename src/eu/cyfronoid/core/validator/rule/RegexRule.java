package eu.cyfronoid.core.validator.rule;

import java.util.regex.Pattern;

public abstract class RegexRule implements Validable<String> {
    private Pattern compiledPattern;
    private String pattern;
    private int caseInsensitive = 0;

    public RegexRule(String pattern) {
        this.pattern = pattern;
    }

    public RegexRule caseInsensitive() {
        caseInsensitive |= Pattern.CASE_INSENSITIVE;
        return this;
    }

    public RegexRule caseSensitive() {
        caseInsensitive &= ~Pattern.CASE_INSENSITIVE;
        return this;
    }

    public RegexRule compile() {
        compiledPattern = Pattern.compile(pattern, caseInsensitive);
        return this;
    }

    @Override
    public boolean validate(String stringToValidate) {
        return compiledPattern.matcher(stringToValidate).matches();
    }
}

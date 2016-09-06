package eu.cyfronoid.core.validator.impl;

import eu.cyfronoid.core.validator.AnnotationValidator;
import eu.cyfronoid.core.validator.annotation.Regex;

public class RegexValidator implements AnnotationValidator<Regex> {

    @Override
    public boolean isValid(Object attr, Regex annotation) {
        return attr.toString().matches(annotation.pattern());
    }

    @Override
    public String getMessage(Class<?> clazz, String fieldName, Object value, Regex annotation) {
        return "Pattern (" + annotation.pattern() + ") is not matched for " + fieldName + " (used by " + clazz.getName() + "). ";
    }

}



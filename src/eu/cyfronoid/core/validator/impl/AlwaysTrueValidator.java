package eu.cyfronoid.core.validator.impl;

import java.lang.annotation.Annotation;

import eu.cyfronoid.core.validator.AnnotationValidator;

public class AlwaysTrueValidator implements AnnotationValidator<Annotation> {

    @Override
    public boolean isValid(Object attr, Annotation annotation) {
        return true;
    }

    @Override
    public String getMessage(Class<?> clazz, String fieldName, Object value, Annotation annotation) {
        return "";
    }

}



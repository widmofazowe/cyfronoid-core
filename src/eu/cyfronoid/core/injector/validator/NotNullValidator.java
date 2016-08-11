package eu.cyfronoid.core.injector.validator;

import java.lang.annotation.Annotation;

public enum NotNullValidator implements BuilderValidator {
    INSTANCE;

    @Override
    public void validate(Annotation annotation, String methodName, Object argument) {
        if (argument == null) {
            throw new IllegalArgumentException("Argument passed to method: " + methodName + " is null");
        }
    }
}
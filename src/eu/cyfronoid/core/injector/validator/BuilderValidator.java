package eu.cyfronoid.core.injector.validator;

import java.lang.annotation.Annotation;

public interface BuilderValidator {
    public void validate(Annotation annotation, String methodName, Object argument);
}

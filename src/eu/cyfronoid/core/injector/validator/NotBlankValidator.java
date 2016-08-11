package eu.cyfronoid.core.injector.validator;

import java.lang.annotation.Annotation;
import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

public enum NotBlankValidator implements BuilderValidator {
    INSTANCE;

    @Override
    public void validate(Annotation annotation, String methodName, Object argument) {
        if(argument == null) {
            throw new IllegalArgumentException("Argument passed to method: " + methodName + " is null");
        }

        if(argument.getClass().isArray()) {
            Arrays.asList((Object[])argument).forEach((arg) -> checkString(methodName, arg));
        } else {
            checkString(methodName, argument);
        }
    }

    private void checkString(String methodName, Object argument) {
        if(argument instanceof String) {
            if(StringUtils.isBlank((String) argument)) {
                throw new IllegalArgumentException("Argument passed to method: " + methodName + " is empty");
            }
        } else {
            throw new IllegalStateException("Annotation: @NotBlank can be used only for String argument, found: "
                    + argument.getClass().getName());
        }
    }
}
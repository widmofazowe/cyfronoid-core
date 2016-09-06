package eu.cyfronoid.core.validator;

public interface AnnotationValidator<T> {
    boolean isValid(Object attr, T annotation);
    String getMessage(Class<?> builderClass, String fieldName, Object value, T annotation);
}

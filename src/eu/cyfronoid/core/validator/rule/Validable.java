package eu.cyfronoid.core.validator.rule;

public interface Validable<T> {
    public boolean validate(T itemToValidate);
}

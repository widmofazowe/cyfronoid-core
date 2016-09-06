package eu.cyfronoid.core.property;

public interface ReadOnlyProperty<T> {
    T get();
    boolean isNull();
    boolean isNotNull();
}

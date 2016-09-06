package eu.cyfronoid.core.property;

public interface Property<T> extends ReadOnlyProperty<T> {
    void set(T value);
}

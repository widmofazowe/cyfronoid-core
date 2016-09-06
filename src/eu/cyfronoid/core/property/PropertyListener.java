package eu.cyfronoid.core.property;

public interface PropertyListener<T> {
    void valueChanged(Property<T> property, T oldValue, T newValue);
}


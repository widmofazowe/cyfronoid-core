package eu.cyfronoid.core.property;

public interface DynamicProperty<T> extends Property<T> {
    void addPropertyListener(PropertyListener<T> listener);
    void removePropertyListener(PropertyListener<T> listener);
}

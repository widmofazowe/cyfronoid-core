package eu.cyfronoid.core.property;

public class PropertyImpl<T> implements Property<T> {
    private T value;

    public PropertyImpl(T value) {
        this.value = value;
    }

    public void set(T value) {
        this.value = value;
    }

    public T get() {
        return value;
    }

    public boolean isNull() {
        return value == null;
    }

    public boolean isNotNull() {
        return ! isNull();
    }

    @Override
    public String toString(){
        return String.valueOf(value);
    }

}

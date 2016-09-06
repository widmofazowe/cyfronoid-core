package eu.cyfronoid.core.property;

public interface ListProperty<T> extends ReadOnlyListProperty<T> {
    boolean add(T item);
    boolean add(int index, T item);
    boolean remove(T item);
}

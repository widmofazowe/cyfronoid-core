package eu.cyfronoid.core.property;

import java.util.List;
import java.util.Set;

public interface ReadOnlyListProperty<T> extends Iterable<T> {
    boolean isEmpty();
    boolean isNotEmpty();
    int count();
    boolean contains(T item);
    List<T> toList();
    Set<T> toSet();
    T get(int index);
}

package eu.cyfronoid.core.collection;

import java.io.Serializable;
import java.util.Collection;

public interface Cache<K extends Serializable, V extends Serializable> {
    void put(K key, V value);
    V get(K key);
    void remove(K key);
    int size();
    void clearCache();
    void closeCache();
    Collection<K> getAllKeys();
}

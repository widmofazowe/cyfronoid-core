package eu.cyfronoid.core.collection;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import com.google.common.collect.Maps;

public class MapCache<K extends Serializable, V extends Serializable> implements Cache<K, V> {
    private final static int DEFAULT_INITIAL_CAPACITY = 128;
    private final Map<K, V> map;

    public MapCache() {
        this(DEFAULT_INITIAL_CAPACITY);
    }

    public MapCache(int initialCapacity) {
        map = Maps.newHashMapWithExpectedSize(initialCapacity);
    }

    @Override
    public void put(K key, V value) {
        map.put(key, value);
    }

    @Override
    public V get(K key) {
        return map.get(key);
    }

    @Override
    public void remove(K key) {
        map.remove(key);
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public void clearCache() {
        map.clear();
    }

    @Override
    public void closeCache() {
    }

    @Override
    public Collection<K> getAllKeys() {
        return map.keySet();
    }
}

package eu.cyfronoid.core.collection;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Maps;

public class CaseInsensitiveMap<V> implements Map<String, V> {
    private final Map<String, V> impl;
    private final Map<String, String> lowerCaseToKey = Maps.newHashMap();

    public static <V> CaseInsensitiveMap<V> createHashMap() {
        return new CaseInsensitiveMap<V>(Maps.newHashMap());
    }

    public static <V> CaseInsensitiveMap<V> createTreeMap() {
        return new CaseInsensitiveMap<V>(Maps.newTreeMap());
    }

    public static <V> CaseInsensitiveMap<V> createLinkedHashMap() {
        return new CaseInsensitiveMap<V>(Maps.newLinkedHashMap());
    }

    public static <V> CaseInsensitiveMap<V> create(Map<String, V> implementation) {
        return new CaseInsensitiveMap<V>(implementation);
    }

    public CaseInsensitiveMap(Map<String, V> implementation) {
        impl = implementation;
    }

    @Override
    public void clear() {
        impl.clear();
    }

    @Override
    public boolean containsKey(Object key) {
        return lowerCaseToKey.containsKey(objectToLowerCase(key));
    }

    @Override
    public boolean containsValue(Object value) {
        return impl.containsValue(value);
    }

    @Override
    public Set<java.util.Map.Entry<String, V>> entrySet() {
        return impl.entrySet();
    }

    @Override
    public V get(Object key) {
        return impl.get(objectToLowerCase(key));
    }

    @Override
    public V getOrDefault(Object key, V defaultValue) {
        return impl.getOrDefault(objectToLowerCase(key), defaultValue);
    }

    @Override
    public boolean isEmpty() {
        return impl.isEmpty();
    }

    @Override
    public Set<String> keySet() {
        return impl.keySet();
    }

    @Override
    public V put(String key, V value) {
        return impl.put(objectToLowerCase(key), value);
    }

    @Override
    public void putAll(Map<? extends String, ? extends V> m) {
        for (Entry<? extends String, ? extends V> entry : m.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public V remove(Object key) {
        return impl.remove(objectToLowerCase(key));
    }

    @Override
    public int size() {
        return impl.size();
    }

    @Override
    public Collection<V> values() {
        return impl.values();
    }

    @Override
    public String toString() {
        return impl.toString();
    }

    private String objectToLowerCase(Object key) {
        if (key == null) {
            return null;
        }
        if (!(key instanceof String)) {
            throw new IllegalArgumentException("Expected string as a key");
        }
        String lowerCase = ((String) key).toLowerCase();
        return lowerCase;
    }

}
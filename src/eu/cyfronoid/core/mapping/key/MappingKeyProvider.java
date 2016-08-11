package eu.cyfronoid.core.mapping.key;

public interface MappingKeyProvider<T> {
    String getKey(T object);
}

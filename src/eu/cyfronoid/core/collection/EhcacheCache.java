package eu.cyfronoid.core.collection;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.PersistenceConfiguration;
import net.sf.ehcache.config.PersistenceConfiguration.Strategy;
import net.sf.ehcache.store.MemoryStoreEvictionPolicy;

public class EhcacheCache<K extends Serializable, V extends Serializable> implements Cache<K, V> {
    private final Ehcache underlyingCache;
    private final String name;

    public EhcacheCache(String name, int maxElementsInMemory) {
        this.name = name;
        this.underlyingCache = new net.sf.ehcache.Cache(new CacheConfiguration(name, maxElementsInMemory)
                .memoryStoreEvictionPolicy(MemoryStoreEvictionPolicy.LRU)
                .maxEntriesInCache(0)
                .persistence(new PersistenceConfiguration().strategy(Strategy.LOCALTEMPSWAP))
                .eternal(true));

        CacheManager cacheManager = CacheManager.getInstance();
        cacheManager.removeCache(name);
        cacheManager.addCache(underlyingCache);
    }

    @Override
    public void put(K key, V value) {
        underlyingCache.put(new Element(key, value));
    }

    @Override
    @SuppressWarnings("unchecked")
    public V get(K key) {
        Element element = underlyingCache.get(key);
        if (element == null) {
            return null;
        }

        return (V) element.getObjectValue();
    }

    @Override
    public void remove(K key) {
        underlyingCache.remove(key);
    }

    public void clearCache() {
        underlyingCache.removeAll();
    }

    public void closeCache() {
        CacheManager.getInstance().removeCache(name);
    }

    @Override
    public int size() {
        return underlyingCache.getSize();
    }

    @Override
    public String toString() {
        Collection<K> keys = getAllKeys();
        Iterator<K> iterator = keys.iterator();
        if (!iterator.hasNext()) {
            return "Empty Cache '" + name + "'";
        }
        StringBuilder builder = new StringBuilder();
        builder.append("Cache '" + name + "' with ");
        builder.append(keys.size());
        builder.append(" elements:\n{");
        for (;;) {
            K key = iterator.next();
            V value = get(key);
            builder.append(key);
            builder.append('=');
            builder.append(value);
            if (!iterator.hasNext()) {
                return builder.append('}').toString();
            }
            builder.append(", ");
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Collection<K> getAllKeys() {
        return underlyingCache.getKeys();
    }

}


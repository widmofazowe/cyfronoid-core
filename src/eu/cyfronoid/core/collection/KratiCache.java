package eu.cyfronoid.core.collection;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import krati.core.segment.ChannelSegmentFactory;
import krati.core.segment.MemorySegmentFactory;
import krati.io.Serializer;
import krati.io.serializer.JavaSerializer;
import krati.io.serializer.StringSerializer;
import krati.store.DataStore;
import krati.store.IndexedDataStore;
import krati.store.SerializableObjectStore;
import krati.util.IndexedIterator;

import org.apache.log4j.Logger;

import eu.cyfronoid.core.util.FileOperation;

public class KratiCache<K extends Serializable, V extends Serializable> implements Cache<K, V> {
    private static final Logger logger = Logger.getLogger(KratiCache.class);
    private final static String KRATICACHE_DIR = "krati";
    private final String name;
    private final SerializableObjectStore<K, V> store;

    private static IndexedDataStore makeIndexedDataStore(String name, int initialCapacity) {
        try {
            logger.debug("Creating cache: " + name);
            String location = KRATICACHE_DIR + File.separator + name;
            File homeDir = new File(location);
            if (homeDir.exists()) {
                try {
                    FileOperation.deleteRecursively(homeDir);
                } catch(IOException e) {
                    try {
                        FileOperation.deleteDirectoryContents(homeDir);
                    } catch(IOException ee) {
                        logger.error("Can't delete " + location);
                    }
                }
            }
            IndexedDataStore indexedDataStore = new IndexedDataStore(homeDir,
                                    initialCapacity, /* capacity */
                                    10000,    /* update batch size */
                                    5,        /* number of update batches required to sync indexes.dat */
                                    16,       /* index segment file size in MB */
                                    new MemorySegmentFactory(),
                                    16,       /* store segment file size in MB */
                                    new ChannelSegmentFactory()
                                    );
            return indexedDataStore;
        } catch (Exception e) {
            throw new RuntimeException("Can't init krati IndexedDataStore", e);
        }
    }

    public static <K extends Serializable, V extends Serializable> KratiCache<K, V> makeJavaSerialiazableCache(String name, int initialCapacity) {
        DataStore<byte[], byte[]> indexedDataStore = makeIndexedDataStore(name, initialCapacity);
        SerializableObjectStore<K, V> objectStore = new SerializableObjectStore<>(indexedDataStore,
                new JavaSerializer<>(), new JavaSerializer<>());
        return new KratiCache<>(name, objectStore);
    }

    public static KratiCache<String, String> makeStringCache(String name, int initialCapacity) {
        DataStore<byte[], byte[]> indexedDataStore = makeIndexedDataStore(name, initialCapacity);
        SerializableObjectStore<String, String> objectStore = new SerializableObjectStore<>(indexedDataStore,
                new StringSerializer(), new StringSerializer());
        return new KratiCache<>(name, objectStore);
    }

    public static <K extends Serializable, V extends Serializable> KratiCache<K, V> makeCustomCache(String name, int initialCapacity,
            Serializer<K> keySerializer, Serializer<V> valueSerializer) {
        DataStore<byte[], byte[]> indexedDataStore = makeIndexedDataStore(name, initialCapacity);
        SerializableObjectStore<K, V> objectStore = new SerializableObjectStore<>(indexedDataStore, keySerializer, valueSerializer);
        return new KratiCache<>(name, objectStore);
    }

    private KratiCache(String name, SerializableObjectStore<K, V> store) {
        this.name = name;
        this.store = store;
    }

    @Override
    public void put(K key, V value) {
        try {
            store.put(key, value);
        } catch (Exception e) {
            throw new RuntimeException("Problem with put", e);
        }
    }

    @Override
    public V get(K key) {
        try {
            return store.get(key);
        } catch (Exception e) {
            throw new RuntimeException("Problem with get", e);
        }
    }

    @Override
    public void remove(K key) {
        try {
            store.delete(key);
        } catch (Exception e) {
            throw new RuntimeException("Problem with remove", e);
        }
    }

    @Override
    public int size() {
        int size = 0;
        IndexedIterator<K> keyIterator = store.keyIterator();
        while (keyIterator.hasNext()) {
            size++;
            keyIterator.next();
        }
        return size;
    }

    @Override
    public void clearCache() {
        try {
            logger.debug("Clearing cache: " + name);
            store.clear();
        } catch (Exception e) {
            throw new RuntimeException("Problem with remove", e);
        }
    }

    @Override
    public void closeCache() {
        try {
            logger.debug("Closing cache: " + name);
            store.close();
        } catch (Exception e) {
            throw new RuntimeException("Problem with close", e);
        }
    }

    @Override
    public String toString() {
        Iterator<Entry<K, V>> iterator = store.iterator();
        if (!iterator.hasNext()) {
            return "Empty Cache '" + name + "'";
        }

        StringBuilder builder = new StringBuilder();
        builder.append("Cache '" + name + "' with ");
        int keyCount = 0;

        StringBuilder valuesBuilder = new StringBuilder();
        for (;;) {
            keyCount++;
            Entry<K, V> entry = iterator.next();
            valuesBuilder.append(entry.getKey());
            valuesBuilder.append('=');
            valuesBuilder.append(entry.getValue());
            if (!iterator.hasNext()) {
                valuesBuilder.append('}');
                break;
            }
            if (keyCount > 1000) {
                valuesBuilder.append(" ... {remainder skipped}");
                while (iterator.hasNext()) {
                    keyCount++;
                    iterator.next();
                }
                break;
            }
            valuesBuilder.append(", ");
        }

        builder.append(keyCount);
        builder.append(" elements:\n{");
        builder.append(valuesBuilder);

        return builder.toString();
    }

    public Collection<K> getAllKeys(){
        List<K> distnames = new ArrayList<K>();
        IndexedIterator<K> keyIterator = store.keyIterator();
        while(keyIterator.hasNext()){
            distnames.add(keyIterator.next());
        }

        return distnames;
    }
}

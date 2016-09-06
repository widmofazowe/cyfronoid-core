package eu.cyfronoid.core.pool;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.google.common.collect.Queues;

public abstract class ObjectPool<T> {
    private final ConcurrentLinkedQueue<T> pool;
    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    public ObjectPool(final int minObjects, final int maxObjects, final long validationInterval) {
        this(minObjects);
        executorService.scheduleWithFixedDelay(new Runnable() {

            @Override
            public void run() {
                int size = pool.size();

                if (size < minObjects) {
                    int sizeToBeAdded = minObjects + size;
                    for (int i = 0; i < sizeToBeAdded; i++) {
                        pool.add(createObject());
                    }
                } else if (size > maxObjects) {
                    int sizeToBeRemoved = size - maxObjects;
                    for (int i = 0; i < sizeToBeRemoved; i++) {
                        pool.poll();
                    }
                }
            }
        }, validationInterval, validationInterval, TimeUnit.SECONDS);
    }

    public ObjectPool(final int minObjects) {
        pool = Queues.newConcurrentLinkedQueue();
        initialize(minObjects);
    }

    public T borrowObject() {
        T object;
        if ((object = pool.poll()) == null) {
            object = createObject();
        }
        return object;
    }

    public void returnObject(T object) {
        if (object == null) {
            return;
        }
        this.pool.offer(object);
    }

    public void shutdown() {
        if(executorService != null) {
            executorService.shutdown();
        }
    }

    protected abstract T createObject();

    private void initialize(final int minObjects) {
        for(int i = 0; i < minObjects; i++) {
            pool.add(createObject());
        }
    }
}

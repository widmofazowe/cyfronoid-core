package eu.cyfronoid.core.injector;

import com.google.inject.Injector;

public interface ReconciliationInjector {
    <T> T getInstance(Class<T> paramClass);
    <T> T getInstance(Class<T> type, NamedProvider namedProvider);
    Injector getInjector();
}

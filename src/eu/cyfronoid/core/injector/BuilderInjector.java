package eu.cyfronoid.core.injector;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.matcher.Matchers;

import eu.cyfronoid.core.injector.validator.BuilderInterceptor;
import eu.cyfronoid.core.injector.validator.ValidateBuilder;

public class BuilderInjector {

    private static Injector builderInjector = Guice.createInjector(new BuilderModule());

    public static <T> T getInstance(Class<T> paramClass) {
        return getInjector().getInstance(paramClass);
    }

    public static Injector getInjector() {
        if(builderInjector == null) {
            throw new RuntimeException("Injector for builder is not intialized.");
        }
        return builderInjector;
    }

    private static class BuilderModule extends AbstractModule {

        @Override
        protected void configure() {
            bindInterceptor(Matchers.annotatedWith(ValidateBuilder.class), Matchers.any(), new BuilderInterceptor());
        }

    }
}

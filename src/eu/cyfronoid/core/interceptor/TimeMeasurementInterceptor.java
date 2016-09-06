package eu.cyfronoid.core.interceptor;

import java.lang.reflect.Method;
import java.text.MessageFormat;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.log4j.Logger;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

import eu.cyfronoid.core.dsp.Spectrum;

public class TimeMeasurementInterceptor implements MethodInterceptor {
    private static final Logger logger = Logger.getLogger(TimeMeasurementInterceptor.class);

    @Override
    public Object invoke(MethodInvocation mi) throws Throwable {
        long startPoint = System.currentTimeMillis();
        Object result = mi.proceed();
        long duration = System.currentTimeMillis() - startPoint;
        Method method = mi.getMethod();
        String message = MessageFormat.format("Invoking {0}.{1}() took {2} ms", method.getDeclaringClass().getSimpleName(), method.getName(), duration);
        logger.debug(message);
        return result;
    }

    //Example
    public static void main(String[] args) throws NotImplementedException {
        Injector injector = Guice.createInjector(new AbstractModule() {

            @Override
            protected void configure() {

            }

        });
        double[] samples = new double[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 9, 9, 9, 9, 7, 6, 5, 4, 3, 2};
        Spectrum complexTask = injector.getInstance(Spectrum.class);
        complexTask.setSamples(samples);
        complexTask.setSampleSize(1024);
        complexTask.fft();
    }

}



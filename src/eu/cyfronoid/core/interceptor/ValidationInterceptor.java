package eu.cyfronoid.core.interceptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import eu.cyfronoid.core.validator.AnnotationValidator;
import eu.cyfronoid.core.validator.ValidationRegistry;

@SuppressWarnings("rawtypes")
public class ValidationInterceptor implements MethodInterceptor {

    private final ValidationRegistry validationRegistry = new ValidationRegistry();

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        checkMethod(methodInvocation.getMethod(), methodInvocation.getArguments());
        return methodInvocation.proceed();
    }

    private void checkMethod(Method method, Object[] arguments) {
        for(int i=0 ; i<arguments.length ; i++) {
            List<Annotation> annotations = Arrays.asList(method.getParameterAnnotations()[i]);
            validateArgument(annotations, method, arguments[i]);
        }
    }

    @SuppressWarnings("unchecked")
    private void validateArgument(List<Annotation> annotations, Method method, Object argument) {
        for(Annotation annotation : annotations) {
            AnnotationValidator builderValidator = getBuilderValidator(annotation);
            if(!builderValidator.isValid(argument, annotation)) {
                throw new IllegalArgumentException(builderValidator.getMessage(method.getDeclaringClass(), method.getName(), argument, annotation));
            }
        }
    }

    private AnnotationValidator getBuilderValidator(Annotation annotation) {
        AnnotationValidator builderValidator = validationRegistry.getValidator(annotation.annotationType());
        if(builderValidator == null) {
            throw new IllegalStateException("Cannot find AnnotationValidator for annotation: " + annotation.annotationType().getName());
        }
        return builderValidator;
    }

}

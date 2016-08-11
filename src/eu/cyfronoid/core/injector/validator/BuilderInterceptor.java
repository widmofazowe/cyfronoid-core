package eu.cyfronoid.core.injector.validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import com.google.common.collect.ImmutableMap;

public class BuilderInterceptor implements MethodInterceptor {

    private final Map<Class<? extends Annotation>, BuilderValidator> validators = ImmutableMap.<Class<? extends Annotation>, BuilderValidator>builder()
            .put(NotBlank.class, NotBlankValidator.INSTANCE)
            .put(NotNull.class, NotNullValidator.INSTANCE)
            .build();

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        checkMethod(methodInvocation.getMethod(), methodInvocation.getArguments());
        return methodInvocation.proceed();
    }

    private void checkMethod(Method method, Object[] arguments) {
        for(int i = 0 ; i < arguments.length ; i++) {
            Object argument = arguments[i];
            List<Annotation> annotations = Arrays.asList(method.getParameterAnnotations()[i]);
            annotations.forEach(ann -> validateArgument(ann, method.getName(), argument));
        }
    }

    private void validateArgument(Annotation annotation, String methodName, Object argument) {
        BuilderValidator builderValidator = getBuilderValidator(annotation);
        builderValidator.validate(annotation, methodName, argument);
    }

    private BuilderValidator getBuilderValidator(Annotation annotation) {
        BuilderValidator builderValidator = validators.get(annotation.annotationType());
        if(builderValidator == null) {
            throw new IllegalStateException("Cannot find BuilderValidator for annotation: " + annotation.annotationType().getName());
        }
        return builderValidator;
    }
}


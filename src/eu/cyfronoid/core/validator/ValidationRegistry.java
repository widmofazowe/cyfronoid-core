package eu.cyfronoid.core.validator;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

import eu.cyfronoid.core.validator.annotation.NotNull;
import eu.cyfronoid.core.validator.annotation.Regex;
import eu.cyfronoid.core.validator.impl.AlwaysTrueValidator;
import eu.cyfronoid.core.validator.impl.NotNullValidator;
import eu.cyfronoid.core.validator.impl.RegexValidator;

public class ValidationRegistry {
    Map<Class<? extends Annotation>, AnnotationValidator<? extends Annotation>> registry = new HashMap<Class<? extends Annotation>, AnnotationValidator<? extends Annotation>>();

    public ValidationRegistry() {
        init();
    }

    private void init() {
        register(NotNull.class, new NotNullValidator());
        register(Regex.class, new RegexValidator());
    }

    public void register(Class<? extends Annotation> annotation, AnnotationValidator<? extends Annotation> validator) {
        if(!registry.containsKey(annotation)) {
            registry.put(annotation, validator);
        }
    }

    public void unregister(Class<? extends Annotation> annotation) {
        registry.put(annotation, null);
    }

    public AnnotationValidator<? extends Annotation> getValidator(Class<? extends Annotation> annotation) {
        AnnotationValidator<? extends Annotation> validator = registry.get(annotation);
        if(validator == null){
            validator = new AlwaysTrueValidator();
        }
        return validator;
    }
}

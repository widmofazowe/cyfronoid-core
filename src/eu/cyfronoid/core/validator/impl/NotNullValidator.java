package eu.cyfronoid.core.validator.impl;

import com.google.common.base.Optional;

import eu.cyfronoid.core.validator.AnnotationValidator;
import eu.cyfronoid.core.validator.annotation.NotNull;

public class NotNullValidator implements AnnotationValidator<NotNull> {

    private boolean validateString(Object obj, boolean acceptEmptyString) {
        if(obj instanceof String) {
            if(((String)obj).trim().length() == 0) {
                if(!acceptEmptyString) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean isValid(Object attr, NotNull annotation) {
        if(attr == null){
            return false;
        }
        if(!validateString(attr, annotation.acceptEmptyString())) {
            return false;
        }

        if(attr instanceof Optional){
            if(!((Optional<?>)attr).isPresent()) {
                return false;
            }
            if(!validateString(((Optional<?>)attr).get(), annotation.acceptEmptyString())){
                return false;
            }
        }
        return true;
    }

    @Override
    public String getMessage(Class<?> clazz, String fieldName, Object value, NotNull annotation) {
        if(annotation.message() != null && annotation.message().trim().length() > 0){
            return annotation.message();
        }
        if(annotation.acceptEmptyString()) {
            return "Null value passed to method " + fieldName + " (used in " + clazz.getName() + ")";
        }
        return "Null or empty value is not valid for " + fieldName + " (used in " + clazz.getName() + ")";
    }

}



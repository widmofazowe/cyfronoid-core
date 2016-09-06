package eu.cyfronoid.core.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;

public class Annotations {

    public static <T extends Annotation> T getAnnotation(Class<?> type, Class<T> annotationType) {
        T annotation = type.getAnnotation(annotationType);
        if(annotation != null) {
            return annotation;
        } else {
            if(type.isInterface()) {
                for(Class<?> extended : type.getInterfaces()) {
                    annotation = getAnnotation(extended, annotationType);
                    if(annotation != null) {
                        return annotation;
                    }
                }
                return null;
            } else {
                Class<?> superType = type.getSuperclass();
                if(!Object.class.equals(superType) && superType != null) {
                    return getAnnotation(superType, annotationType);
                } else {
                    return null;
                }
            }
        }
    }


    public static boolean isAnnotationPresent(Class<?> type, Class<? extends Annotation> annotationType) {
        if(type.isAnnotationPresent(annotationType)) {
            return true;
        } else {
            if (type.isInterface()) {
                for(Class<?> extended : type.getInterfaces()) {
                    if(isAnnotationPresent(extended, annotationType)) {
                        return true;
                    }
                }
                return false;
            } else {
                Class<?> superType = type.getSuperclass();
                if(!Object.class.equals(superType) && superType != null) {
                    return isAnnotationPresent(superType, annotationType);
                } else {
                    return false;
                }
            }
        }
    }

    public static <TYPE extends Annotation> TYPE getAnnotation(AccessibleObject object, Class<TYPE> annotationType) {
        for(Annotation annotation : object.getDeclaredAnnotations()) {
            if(annotation.annotationType().equals(annotationType)) {
                return annotationType.cast(annotation);
            }
        }
        return null;
    }
}

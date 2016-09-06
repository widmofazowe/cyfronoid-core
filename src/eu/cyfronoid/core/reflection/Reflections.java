package eu.cyfronoid.core.reflection;

import java.lang.reflect.Field;

import org.apache.log4j.Logger;

public class Reflections {
    private static final Logger logger = Logger.getLogger(Reflections.class);

    public static <T> T getInstance(Class<T> clazz) {
        try {
            T instance = clazz.newInstance();
            return instance;
        } catch (InstantiationException | IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static <T> T getPrivateField(Object obj, Class<T> fieldClass, String fieldName) {
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            if(field.getName().equals(fieldName)) {
                field.setAccessible(true);
                try {
                    return fieldClass.cast(field.get(obj));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        throw new RuntimeException("Not found field with class " + fieldClass.getSimpleName());
    }

    public static void setField(Object o, String fieldName, Object fieldValue) {
        try {
            Field field = o.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(o, fieldValue);
        } catch (Exception e) {
            logger.error("setValue - " + e);
        }
    }

}

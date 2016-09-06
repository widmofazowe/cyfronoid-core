package eu.cyfronoid.core.property;

import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import eu.cyfronoid.core.reflection.Annotations;

public class PropertyInjector {
    private static final Logger log = Logger.getLogger(PropertyInjector.class);
    private static final PropertyInjector instance = new PropertyInjector();
    private Properties properties = new Properties();

    private PropertyInjector() { }

    public static PropertyInjector getInstance() {
        return instance;
    }

    public Properties getProperties() {
        return this.properties;
    }

    public void registerProperties(String... fileName) {
        try {
            for (String file : fileName) {
                registerProperties(new FileInputStream(file));
            }
        } catch (Exception e) {
            throw new RuntimeException("{registerProperties}", e);
        }
    }

    public void registerProperties(InputStream... propertyStreams) {
        try {
            for (InputStream propertyStream : propertyStreams) {
                properties.load(propertyStream);
            }
        } catch (Exception e) {
            throw new RuntimeException("{registerProperties}", e);
        }
    }

    private static List<Field> getAllFields(Class<?> type) {
        List<Field> fields = new ArrayList<Field>();
        for (Class<?> c = type; c != null; c = c.getSuperclass()) {
            fields.addAll(Arrays.asList(c.getDeclaredFields()));
        }
        return fields;
    }

    public void injectProperties(Object object) {
        if (null != object) {
            for (Field field : PropertyInjector.getAllFields(object.getClass())) {
                PropertyInject annotation = Annotations.getAnnotation(field, PropertyInject.class);
                if (null != annotation) {
                    if (null == annotation.name() || "".equals(annotation.name())) {
                        throw new RuntimeException("{injectProperties} @PropertyInject.name() on " +
                                object.getClass().getName() + " on field " + field.getName() + " is empty!");
                    }
                    field.setAccessible(true);
                    try {
                        Object defaultValue = field.get(object);
                        if(!injectProperty(object, field, annotation)) {
                            if(annotation.required()) {
                                throw new Exception("Required property could not be injected! (" +
                                        annotation.name() + " into " + field.getName() + " of " +
                                        object.getClass().getName() + ")");
                            } else if(defaultValue == null) {
                                log.debug("{injectProperty} Not required property wasn't initialized by injection " +
                                        "nor default value (" + field.getName() +
                                        " of " + object.getClass().getName() + ")");
                            }
                        }
                    } catch (RuntimeException e) {
                        throw e;
                    } catch (Exception e) {
                        throw new RuntimeException("{injectProperties}", e);
                    }
                }
            }

        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private boolean injectProperty(Object object, Field field, PropertyInject annotation) {
        try {
            String value = properties.getProperty(annotation.name());
            if (null != value) {
                if (String.class.equals(field.getType())) {
                    field.set(object, value);
                } else if (Integer.class.equals(field.getType()) || int.class.equals(field.getType())) {
                    field.set(object, Integer.parseInt(value));
                } else if (Long.class.equals(field.getType()) || long.class.equals(field.getType())) {
                    field.set(object, Long.parseLong(value));
                } else if (Double.class.equals(field.getType()) || double.class.equals(field.getType())) {
                    field.set(object, Double.parseDouble(value));
                } else if (Float.class.equals(field.getType()) || float.class.equals(field.getType())) {
                    field.set(object, Float.parseFloat(value));
                } else if (Boolean.class.equals(field.getType()) || boolean.class.equals(field.getType())) {
                    field.set(object, Boolean.parseBoolean(value));
                } else if (Class.class.equals(field.getType())) {
                    field.set(object, Class.forName(value));
                } else if (field.getType().isEnum()) {
                    field.set(object, Enum.valueOf((Class<? extends Enum>)field.getType(), value));
                } else {
                    log.warn("{injectProperty} unable to inject property on " + object.getClass().getName() +
                            " on field " + field.getName() + " with value=" + value + " - not recognized type");
                    return false;
                }
                return true;
            }

            return false;
        } catch (Exception e) {
            throw new RuntimeException("{injectProperty}", e);
        }
    }
}


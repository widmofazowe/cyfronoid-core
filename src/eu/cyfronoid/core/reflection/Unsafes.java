package eu.cyfronoid.core.reflection;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import org.apache.log4j.Logger;

import sun.misc.Unsafe;
import eu.cyfronoid.core.text.Strings;

public class Unsafes {
    final private static Logger logger = Logger.getLogger(Unsafe.class);
    final private static char FAKE_PASSWORD_ITEM = '?';
    private static Unsafe unsafe;

    static {
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            unsafe = (Unsafe) f.get(null);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            logger.warn("Cannot create instance of Unsafe class because of: " + e);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T createInstanceOf(Class<T> clazz) throws InstantiationException {
        checkUnsafeClassCreated();
        return (T) unsafe.allocateInstance(clazz);
    }

    public static void clearPassword(String password) {
        int length = password.length();
        String fake = Strings.createFilledString(length, FAKE_PASSWORD_ITEM);
        unsafe.copyMemory(fake, 0L, null, addressOf(password), length);
    }

    public static long addressOf(Object o) {
        checkUnsafeClassCreated();
        Object[] array = new Object[] {o};
        long baseOffset = unsafe.arrayBaseOffset(Object[].class);
        int addressSize = unsafe.addressSize();
        long objectAddress;
        switch (addressSize) {
        case 4:
            objectAddress = unsafe.getInt(array, baseOffset);
            break;
        case 8:
            objectAddress = unsafe.getLong(array, baseOffset);
            break;
        default:
            throw new RuntimeException("Unsupported address size: " + addressSize);
        }
        return objectAddress;
    }

    public static Object createInstanceOfClassFromFile(String pathToClassFile) //path to a java .class file
            throws IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
            NoSuchMethodException, SecurityException, InstantiationException {
        checkUnsafeClassCreated();
        byte[] classContents = getClassContent(pathToClassFile);
        Class<?> c = unsafe.defineClass(null, classContents, 0, classContents.length, null, null);
        return c.getMethod("a").invoke(c.newInstance(), (Object)null);
    }

    private static byte[] getClassContent(String pathToClassFile) throws IOException {
        File f = new File(pathToClassFile);
        FileInputStream input = new FileInputStream(f);
        byte[] content = new byte[(int)f.length()];
        input.read(content);
        input.close();
        return content;
    }

    private static void checkUnsafeClassCreated() {
        if(!isUsable()) {
            throw new RuntimeException("Cannot use UnsafeUtil class because instance of Unsafe cannot be created");
        }
    }

    public static boolean isUsable() {
        return unsafe != null;
    }
}

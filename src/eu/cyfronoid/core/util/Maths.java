package eu.cyfronoid.core.util;

public class Maths {
    public static boolean isPowerOf2(int x) {
        return ((x - 1) & x) == 0;
    }
}

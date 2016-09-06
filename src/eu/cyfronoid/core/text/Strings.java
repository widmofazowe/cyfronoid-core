package eu.cyfronoid.core.text;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Strings {
    final private static String ALPHABET = "absdefghijklmnopqrstuvwxyz";

    public static String implode(List<String> inputList, String glueString) {
        return eu.cyfronoid.core.util.Arrays.implode(inputList.toArray(new String[inputList.size()]), glueString);
    }

    public static String implode(String[] inputArray, String glueString) {
        return eu.cyfronoid.core.util.Arrays.implode(inputArray, glueString);
    }

    public static String[] getNValues(String[] array, int start, int n) {
        String[] resultArray = new String[n];
        for(int i = 0, j=start; i < n && j < array.length; ++i, ++j) {
            resultArray[i] = array[j];
        }
        return resultArray;
    }

    public static boolean anyContains(String[] array, String part) {
        for(String element : array) {
            if(element.contains(part)) {
                return true;
            }
        }
        return false;
    }

    public static boolean anyStartsWith(String[] array, String prefix) {
        for(String element : array) {
            if(element.startsWith(prefix)) {
                return true;
            }
        }
        return false;
    }

    public static String generateString(int length) {
        Random generator = new Random();
        char[] text = new char[length];
        for (int i = 0; i < length; i++) {
            text[i] = ALPHABET.charAt(generator.nextInt(ALPHABET.length()));
        }
        return new String(text);
    }

    public static String generateString(int length, String characters) {
        Random generator = new Random();
        char[] text = new char[length];
        for (int i = 0; i < length; i++) {
            text[i] = characters.charAt(generator.nextInt(characters.length()));
        }
        return new String(text);
    }

    public static byte[] toByteArray(String str) {
        return toByteArray(str.toCharArray(), Charset.defaultCharset());
    }

    public static byte[] toByteArray(char[] array) {
        return toByteArray(array, Charset.defaultCharset());
    }

    public static byte[] toByteArray(char[] array, Charset charset) {
        CharBuffer cbuf = CharBuffer.wrap(array);
        ByteBuffer bbuf = charset.encode(cbuf);
        return bbuf.array();
    }

    public static String surroundWith(String item, String surroundingChar) {
        return surroundingChar + item + surroundingChar;
    }

    public static String[] surroundEveryWith(String[] items, String surroundingChar) {
        String[] surrounded = new String[items.length];
        for(int i = 0; i < items.length; ++i) {
            surrounded[i] = surroundWith(items[i], surroundingChar);
        }
        return surrounded;
    }

    public static String addParentheses(String item) {
        return "(" + item + ")";
    }

    public static String lowerFirstChar(String string) {
        return Character.toLowerCase(
                  string.charAt(0)) + (string.length() > 1 ? string.substring(1) : "");
    }

    public static String toHex(String string) {
        return toHex(string.getBytes());
    }

    public static String toHex(byte[] array) {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if(paddingLength > 0) {
            return String.format("%0"  +paddingLength + "d", 0) + hex;
        } else {
            return hex;
        }
    }

    public static String truncate(String string, int limit) {
        if(string == null) {
            return "";
        }
        if(limit < 0) {
            return string;
        }
        if(string.length() > limit) {
            StringBuilder result = new StringBuilder(limit + 3);
            result.append(string.substring(0, limit));
            result.append("...");
            return result.toString();
        }
        return string;
    }

    public static String createFilledString(int length, char c) {
        char[] chars = new char[length];
        Arrays.fill(chars, c);
        return new String(chars);
    }
}

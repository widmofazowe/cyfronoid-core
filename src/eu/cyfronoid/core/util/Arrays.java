package eu.cyfronoid.core.util;

public class Arrays {

    public static <T> String implode(T[] inputArray, String separator) {
        String output = "";
        if(inputArray.length > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append(inputArray[0]);
            for (int i = 1; i < inputArray.length; i++) {
                sb.append(separator);
                sb.append(inputArray[i]);
            }
            output = sb.toString();
        }
        return output;
    }

    public static <T> T[] fill(T[] array, T value) {
        for(int i = 0; i < array.length; ++i) {
            array[i] = value;
        }
        return array;
    }

}



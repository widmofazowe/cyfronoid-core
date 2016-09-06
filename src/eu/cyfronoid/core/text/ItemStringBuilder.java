package eu.cyfronoid.core.text;

import java.text.MessageFormat;

import eu.cyfronoid.core.util.Constants;

public class ItemStringBuilder implements Cloneable {
    private final static String LONG_EMPTY_STRING;
    static {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            builder.append("                    ");
        }
        LONG_EMPTY_STRING = builder.toString();
    }

    private final String separator;
    private final StringBuilder builder;
    private int itemsCount;

    public ItemStringBuilder() {
        this(Constants.NEW_LINE.value);
    }

    public ItemStringBuilder(String separator) {
        builder = new StringBuilder();
        this.separator = separator;
        itemsCount = 0;
    }

    @Override
    public ItemStringBuilder clone() {
        if (itemsCount == 0) {
            return new ItemStringBuilder(separator);
        }
        ItemStringBuilder copy = new ItemStringBuilder(separator).append(toString());
        copy.itemsCount = itemsCount;
        return copy;
    }

    public StringBuilder getStringBuilder() {
        return builder;
    }

    public ItemStringBuilder append(String line, Object... objects) {
        preAppending();

        if (objects.length != 0) {
            builder.append(MessageFormat.format(line, objects));
        } else {
            builder.append(line);
        }
        return this;
    }

    public ItemStringBuilder appendWithLength(Object s, int length) {
        preAppending();

        String string = String.valueOf(s);
        builder.append(string);
        int diff = length - string.length();
        if (diff > 0) {
            // add extra spaces
            int remainder = diff;
            while (remainder > LONG_EMPTY_STRING.length()) {
                builder.append(LONG_EMPTY_STRING);
                remainder -= LONG_EMPTY_STRING.length();
            }
            builder.append(LONG_EMPTY_STRING.substring(0, remainder));
        }
        return this;
    }

    private void preAppending() {
        if (itemsCount > 0) {
            builder.append(separator);
        }
        itemsCount++;
    }

    public ItemStringBuilder appendEmpty() {
        preAppending();
        return this;
    }

    public ItemStringBuilder appendObjects(Iterable<?> appendedObjects) {
        appendedObjects.forEach(o -> append(String.valueOf(o)));
        return this;
    }

    public ItemStringBuilder appendObjectsWithIndent(Iterable<?> appendedObjects, String indent) {
        appendedObjects.forEach(o -> {
            preAppending();
            builder.append(indent);
            builder.append(String.valueOf(o));
        });
        return this;
    }

    public String toString() {
        return builder.toString();
    }

    public int getItemsCount() {
        return itemsCount;
    }

}


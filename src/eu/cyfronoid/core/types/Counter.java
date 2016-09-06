package eu.cyfronoid.core.types;

public class Counter {
    private int counter;

    public Counter() {
        reset();
    }

    public Counter inc() {
        ++counter;
        return this;
    }

    public int getValue() {
        return counter;
    }

    public final Counter reset() {
        counter = 0;
        return this;
    }

    @Override
    public String toString() {
        return convertToString();
    }

    private String convertToString() {
        return String.valueOf(counter + ". ");
    }
}

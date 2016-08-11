package eu.cyfronoid.core.configuration.evaluator;

import java.util.ArrayDeque;

public class Stack extends ArrayDeque<Double> {
    private static final long serialVersionUID = 1L;

    @Override
    public void push(Double v) {
        super.push(v);
    }

    @Override
    public Double pop() {
        Double v = super.pop();
        return v;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        for (Double v: this) {
            builder.append(v);
            builder.append(" ");
        }
        builder.append("]");

        return builder.toString();
    }
}

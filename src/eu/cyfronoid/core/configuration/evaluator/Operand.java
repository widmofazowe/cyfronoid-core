package eu.cyfronoid.core.configuration.evaluator;


public class Operand implements Token {
    private Double value;

    Operand(Double value) {
        this.value = value;
    }

    @Override
    public Stack execute(Stack stack) {
        stack.push(value);
        return stack;
    }
}

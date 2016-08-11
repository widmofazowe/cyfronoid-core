package eu.cyfronoid.core.configuration.evaluator;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

public class ReversePolishNotation {
    private Map<String, Token> tokens = ImmutableMap.<String, Token>builder()
            .put("+", new Add())
            .put("-", new Sub())
            .put("*", new Mul())
            .put("/", new Div())
            .put("%", new Mod())
            .build();

    protected Token createToken(String s) {
        Token result = tokens.get(s);
        if (result == null) {
            result = new Operand(Double.valueOf(s));
        }
        return result;
    }

    public double execute(String input) {
        Stack stack = new Stack();

        String[] splits = input.split("\\s");
        if (splits != null && 0 < splits.length) {
            for (String s : splits) {
                Token token = createToken(s);
                token.execute(stack);
            }
        }
        return stack.pop();
    }


    public static void main(String... args) {
        ReversePolishNotation rpn = new ReversePolishNotation();

        // (3 + 2) * 4 == 20
        System.out.println("3 2 + 4 * = "
                   + rpn.execute("3 2 + 4 *"));

        // (1 + 4) * (3 + 7) / 5 == 10
        System.out.println("1 4 + 3 7 + * 5 / = "
                   + rpn.execute("1 4 + 3 7 + * 5 /"));

        // (10 + 21) == 31
        System.out.println("10 21 + = "
                   + rpn.execute("10 21 + "));
    }
}

package eu.cyfronoid.core.configuration;

import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

import eu.cyfronoid.core.configuration.evaluator.ReversePolishNotation;

public class Mapper {
    private static final String DOUBLE_TO_INT = "%.0f";
    private static final String INT_FORMAT = "%d";
    private static final ReversePolishNotation RPN = new ReversePolishNotation();
    private static final Pattern EVALUATOR_PATTERN = Pattern.compile("#(.*?)#");

    private final Map<Pattern, String> mapping = Maps.newHashMap();

    public Mapper(Map<String, String> mapping) {
        Preconditions.checkNotNull(mapping);
        mapping.forEach((k, v) -> this.mapping.put(Pattern.compile(k), v));
    }

    public Optional<String> getMappedValue(String mapKey) {
        for(Map.Entry<Pattern, String> entry : mapping.entrySet()) {
            Matcher matcher = entry.getKey().matcher(mapKey);
            if(matcher.matches()) {
                return Optional.of(formatFromMatcher(matcher, entry.getValue()));
            }
        }

        return Optional.empty();
    }

    public static String formatFromMatcher(Matcher matcher, String format) {
        int groupCount = matcher.groupCount();
        String id = format;
        for (int i = 1; i <= groupCount; ++i) {
            id = id.replace("{" + i + "}", matcher.group(i).trim());
        }

        return evaluateExpressionsInRPN(id);
    }

    private static String evaluateExpressionsInRPN(String id) {
        String evaluatedId = id;
        Matcher matcher = EVALUATOR_PATTERN.matcher(evaluatedId);
        while(matcher.find()) {
            String[] expression = matcher.group(1).split("\\|");
            evaluatedId = matcher.replaceFirst(parseAs(RPN.execute(expression[0]), (expression.length > 1) ? expression[1] : INT_FORMAT));
            matcher = EVALUATOR_PATTERN.matcher(evaluatedId);
        }

        return evaluatedId;
    }

    private static String parseAs(double value, String parseType) {
        if(INT_FORMAT.equals(parseType)) {
            parseType = DOUBLE_TO_INT;
        }

        return String.format(parseType, value);
    }

}

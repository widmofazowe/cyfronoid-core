package eu.cyfronoid.core.configuration;

import static org.junit.Assert.assertEquals;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class MapperTest {

    @Test
    public void testGroupCatching() {
        Matcher matcher = Pattern.compile("ASDF(\\d)").matcher("ASDF3");
        matcher.find();
        assertEquals("3", Mapper.formatFromMatcher(matcher, "{1}"));
    }

    @Test
    public void testEvaluation() {
        Matcher matcher = Pattern.compile("ASDF(\\d)").matcher("ASDF3");
        matcher.find();
        assertEquals("7", Mapper.formatFromMatcher(matcher, "#{1} 4 +|%d#"));
    }

    @Test
    public void testMultipleEvaluation() {
        Matcher matcher = Pattern.compile("ASDF(\\d)-(\\d\\d)").matcher("ASDF3-23");
        matcher.find();
        assertEquals("7 - 23 - 19", Mapper.formatFromMatcher(matcher, "#{1} 4 +|%d# - {2} - #{2} 4 -|%d#"));
    }

    @Test
    public void test() {
        Matcher matcher = Pattern.compile("Gigabit(\\d)/(\\d)").matcher("Gigabit2/3");
        matcher.find();
        assertEquals("Gi7", Mapper.formatFromMatcher(matcher, "Gi#{1} 2 * {2} +#"));
    }


}

package eu.cyfronoid.core.benchmark;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import eu.cyfronoid.core.output.Output;

public class TimerTest {
    final private static String testTimerName = "cyfron";

    public TimerTest() {
        Output.appendConsoleToGlobal();
    }

    @Test
    public void testTimerMethods() {
        Timer timer = new Timer(testTimerName);
        assertTrue("Timer did not start.", timer.getStart() > 0);
        assertTrue("Timer unexpectacly stopped.", !timer.hasEnded());
        timer.stop();
        assertTrue("Timer did not stop.", timer.getStop() > 0);
        assertTrue("Timer measured time is less then 0.", timer.getElapsedNanoSecs() > 0);
        Output.getInstance().println("Testing timer passed. Time measured: " + timer.getElapsedNanoSecs() + " nano seconds.");
    }
}
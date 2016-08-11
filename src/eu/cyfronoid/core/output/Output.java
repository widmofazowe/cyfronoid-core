package eu.cyfronoid.core.output;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import eu.cyfronoid.core.output.appender.ConsoleOutputAppender;

public class Output {
    private static volatile Output globalOutput = null;
    private List<Printable> appenders = new ArrayList<>();

    public static Output getInstance() {
        if(globalOutput == null) {
            synchronized(Output.class) {
                if(globalOutput == null) {
                    globalOutput = new Output();
                }
            }
        }
        return globalOutput;
    }

    public Output append(Printable appender) {
        appenders.add(appender);
        return this;
    }

    public void clearAppenders() {
        appenders.clear();
    }

    public Output appendConsole() {
        append(new ConsoleOutputAppender());
        return this;
    }

    public static void appendConsoleToGlobal() {
        Output output = Output.getInstance();
        ConsoleOutputAppender console = new ConsoleOutputAppender();
        if(!output.appenders.contains(console)) {
            output.append(console);
        }
    }

    public void print(String message) {
        Iterator<Printable> iterator = appenders.iterator();
        while(iterator.hasNext()) {
            iterator.next().print(message);
        }
    }

    public void println(String message) {
        Iterator<Printable> iterator = appenders.iterator();
        while(iterator.hasNext()) {
            iterator.next().println(message);
        }
    }

    public static void print(String message, Printable appender) {
        appender.print(message);
    }

    public static void println(String message, Printable appender) {
        appender.println(message);
    }
}

package eu.cyfronoid.core.output.appender;

import eu.cyfronoid.core.output.Printable;

public class ConsoleOutputAppender implements Printable {

    @Override
    public void print(String message) {
        System.out.print(message);
    }

    @Override
    public void println(String message) {
        System.out.println(message);
    }

}

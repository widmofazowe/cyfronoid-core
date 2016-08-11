package eu.cyfronoid.core.benchmark;


public class NonExistingTimerMarkerException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    NonExistingTimerMarkerException() {
        super("Unknown Exception thrown by NonExistingTimerMarkerException");
    }

    NonExistingTimerMarkerException(String info) {
        super(info);
    }
}
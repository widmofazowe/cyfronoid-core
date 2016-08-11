package eu.cyfronoid.core.benchmark;

import org.apache.log4j.Logger;

public abstract class AbstractTimer {
    final private static Logger logger = Logger.getLogger(AbstractTimer.class);
    protected long filterElapsedNanoSecs = 0;
    protected String markerName = "";
    protected long start = 0;
    protected long stop = 0;
    protected long elapsedSecs = 0;

    public void setFilterElapsedNanoSeconds(long minimumNanoSeconds) {
        filterElapsedNanoSecs = minimumNanoSeconds;
    }

    private long getNanoTime() {
        return System.nanoTime();
    }

    public void stop() {
        stop = getNanoTime();
    }

    public void start() {
        start = getNanoTime();
    }

    public boolean hasEnded() {
        return (stop > start);
    }

    public long getStart() {
        return start;
    }

    public long getStop() {
        return stop;
    }

    public String getMarkerName() {
        return markerName;
    }

    public long getElapsedNanoSecs() {
        if(!hasEnded()) {
            stop();
        }
        return timeElapsed();
    }

    private long timeElapsed() {
        return (stop - start);
    }

    public void display() {
        System.out.println(toString());
    }

    @Override
    public String toString() {
        if (!hasEnded()) {
            stop();
        }

        return "Operation '" + markerName + "' has been proceed in " + getElapsedNanoSecs() + " nano seconds.";
    }

    public void debug() {
        if(filterElapsedNanoSecs == 0 || getElapsedNanoSecs() > filterElapsedNanoSecs) {
            logger.debug(toString());
        }
    }

    public static long toMiliSeconds(long timeInNanoSeconds) {
        return timeInNanoSeconds/1000000;
    }
}
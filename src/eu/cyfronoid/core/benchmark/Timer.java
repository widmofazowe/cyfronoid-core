package eu.cyfronoid.core.benchmark;

public class Timer extends AbstractTimer {

    public Timer(String markerName) {
        this(markerName, true);
    }

    public Timer(String markerName, boolean autostart) {
        this.markerName = markerName;
        if(autostart) {
            start();
        }
    }
}
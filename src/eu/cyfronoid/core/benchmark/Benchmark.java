package eu.cyfronoid.core.benchmark;

import java.util.Map;

import org.apache.log4j.Logger;

import com.google.common.collect.Maps;

public class Benchmark {
    final private static Logger logger = Logger.getLogger(Benchmark.class);
    private static Map<String, Timer> markers = Maps.newHashMap();

    public static void setMarker(String name) {
        markers.put(name, new Timer(name));
    }

    public long getTime(String name) {
        return (!markers.containsKey(name))? markers.get(name).getElapsedNanoSecs() : 0;
    }

    public static void removeMarker(String name) {
        if(markers.containsKey(name)) {
            markers.remove(name);
        }
    }

    public static void start(String name) {
        if(!markers.containsKey(name)) {
            try {
                throw new NonExistingTimerMarkerException("Marker '" + name + "' does not exists.");
            } catch (NonExistingTimerMarkerException e) {
                logger.warn(e.getMessage());
            }
        }
        markers.get(name).start();
    }

    public static void stop(String name) {
        if(!markers.containsKey(name)) {
            try {
                throw new NonExistingTimerMarkerException("Marker '" + name + "' does not exists.");
            } catch (NonExistingTimerMarkerException e) {
                logger.warn(e.getMessage());
            }
        }
        markers.get(name).stop();
    }

    public static long timeElapsed(String name) {
        if(!markers.containsKey(name)) {
            try {
                throw new NonExistingTimerMarkerException("Marker '" + name + "' does not exists.");
            } catch (NonExistingTimerMarkerException e) {
                logger.warn(e.getMessage());
            }
        }
        return markers.get(name).getElapsedNanoSecs();
    }

    public static void display() {
        for(Map.Entry<String, Timer> marker : markers.entrySet()) {
            marker.getValue().display();
        }
    }

    public static void display(String name) {
        if(!markers.containsKey(name)) {
            try {
                throw new NonExistingTimerMarkerException("Marker '" + name + "' does not exists.");
            } catch (NonExistingTimerMarkerException e) {
                logger.warn(e.getMessage());
            }
        }
        markers.get(name).display();
    }

    public static String fetch() {
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<String, Timer> marker : markers.entrySet()) {
            sb.append(marker.getValue().toString()).append('\n');
        }
        return sb.toString();
    }

    public static String fetch(String name) {
        if(markers.containsKey(name)) {
            return markers.get(name).toString();
        } else {
            try {
                throw new NonExistingTimerMarkerException("Marker '" + name + "' does not exists.");
            } catch (NonExistingTimerMarkerException e) {
                logger.warn(e.getMessage());
                return name + " - non existing.";
            }
        }
    }

    public static void debug() {
        for(Map.Entry<String, Timer> marker : markers.entrySet()) {
            marker.getValue().debug();
        }
    }

    public static void debug(String name) {
        if(!markers.containsKey(name)) {
            try {
                throw new NonExistingTimerMarkerException("Marker '" + name + "' does not exists.");
            } catch (NonExistingTimerMarkerException e) {
                logger.warn(e.getMessage());
            }

        }
        markers.get(name).debug();
    }

    /*
    public static function setTest($sFuncName, $aParams = array()) {
        self::$_aTests[$sFuncName] = new Cyfron_Benchmark_Iterator($sFuncName, $aParams);
    }

    public static function run($sFuncName, $iIter = 1) {
        if(!isset(self::$_aTests[$sFuncName]))
            throw new Cyfron_Benchmark_Exception(sprintf('Test %s does not exists.', $sFuncName));
        return self::$_aTests[$sFuncName]->run($iIter);
    }
    */
}

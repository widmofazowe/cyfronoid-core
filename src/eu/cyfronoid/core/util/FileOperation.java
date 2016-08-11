package eu.cyfronoid.core.util;

import java.io.File;

public class FileOperation {

    public static String formatDirPath(String dir) {
        if(!dir.endsWith(String.valueOf(File.separatorChar))) {
            dir = dir + File.separatorChar;
        }

        return dir;
    }
}



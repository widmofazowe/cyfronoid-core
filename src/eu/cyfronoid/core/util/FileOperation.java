package eu.cyfronoid.core.util;

import java.io.File;
import java.io.IOException;

import com.google.common.base.Preconditions;

public class FileOperation {

    public static String formatDirPath(String dir) {
        if(!dir.endsWith(String.valueOf(File.separatorChar))) {
            dir = dir + File.separatorChar;
        }

        return dir;
    }

    public static void deleteRecursively(File file) throws IOException {
        if (file.isDirectory()) {
            deleteDirectoryContents(file);
        }

        if (!file.delete()) {
            throw new IOException("Failed to delete " + file);
        }
    }

    public static void deleteDirectoryContents(File directory) throws IOException {
        Preconditions.checkArgument(directory != null && directory.isDirectory(), "Not a directory: %s", directory);
        // Symbolic links will have different canonical and absolute paths
        if (!directory.getCanonicalPath().equals(directory.getAbsolutePath())) {
            throw new RuntimeException("Symbolic links are not supported - " + directory.getCanonicalPath());
        }
        File[] files = directory.listFiles();
        if (files == null) {
            throw new IOException("Error listing files for " + directory);
        }
        for (File file : files) {
            deleteRecursively(file);
        }
    }

}



package eu.cyfronoid.core.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;

public class FileUtil {
    private final static Logger logger = Logger.getLogger(FileUtil.class);

    public static String formatDirPath(String dir) {
        if(!dir.endsWith(File.separator)) {
            dir = dir + File.separator;
        }
        return dir;
    }

    public static String formatFileName(String fileName, String extension) {
        if(extension.charAt(0) != '.') {
            extension = "." + extension;
        }
        if(!fileName.endsWith(extension)) {
            fileName = fileName + extension;
        }
        return fileName;
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

    public static void deleteRecursively(File file) throws IOException {
        if (file.isDirectory()) {
            deleteDirectoryContents(file);
        }
        if (!file.delete()) {
            throw new IOException("Failed to delete " + file);
        }
    }

    public static List<File> listFiles(File startingDirectory) {
        ImmutableList.Builder<File> builder = ImmutableList.builder();
        File[] files = startingDirectory.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                builder.add(file);
            } else if (file.isDirectory()) {
                continue;
            } else {
                logger.warn("Not a file and not a directory for " + file.getPath());
            }
        }

        return builder.build();
    }

    public static Multimap<FileType, File> listFilesAndDirectories(File startingDirectory) {
        return listFilesAndDirectories(startingDirectory, Predicates.<File>alwaysTrue());
    }

    public static Multimap<FileType, File> listFilesAndDirectories(
            File startingDirectory, Predicate<File> predicate) {
        ImmutableMultimap.Builder<FileType, File> builder = ImmutableMultimap.builder();
        File[] files = startingDirectory.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                if(predicate.apply(file)) {
                    builder.put(FileType.FILE, file);
                }
            } else if (file.isDirectory()) {
                builder.put(FileType.DIRECTORY, file);
            } else {
                logger.warn("Not a file and not a directory for " + file.getPath());
            }
        }

        return builder.build();
    }

    public static List<File> listFilesRecursively(File startingDirectory) {
        return listFilesRecursively(startingDirectory, Predicates.<File>alwaysTrue());
    }

    public static List<File> listFilesRecursively(File startingDirectory, Predicate<File> predicate) {
        ImmutableList.Builder<File> builder = ImmutableList.builder();
        File[] files = startingDirectory.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                if(predicate.apply(file)) {
                    builder.add(file);
                }
            } else if (file.isDirectory()) {
                builder.addAll(listFilesRecursively(file, predicate));
            } else {
                logger.warn("Not a file and not a directory for " + file.getPath());
            }
        }

        return builder.build();
    }

    public static void saveLine(String path, String line) throws FileNotFoundException {
        saveLine(new File(path), line);
    }

    public static void saveLine(File file, String line) throws FileNotFoundException {
        PrintWriter output = new PrintWriter(file);
        output.print(line);
        output.close();
    }

    public static String readLine(String path) throws FileNotFoundException {
        return readLine(new File(path));
    }

    public static String readLine(File file) throws FileNotFoundException {
        Scanner input = new Scanner(file);
        String output = input.nextLine();
        input.close();
        return output;
    }

    public static enum FileType {
        DIRECTORY,
        FILE,
        ;
    }

    public static Optional<String> getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return Optional.fromNullable(ext);
    }

    public static void craeteDirIfNotExisting(String directory) {
        File dir = new File(directory);
        if(!dir.isDirectory()) {
            logger.debug("Creating path: " + directory);
            dir.mkdirs();
        }
    }

}



package eu.cyfronoid.core.file;

import java.io.File;
import java.io.FileFilter;

import com.google.common.collect.ImmutableSet;
import com.google.common.io.Files;

public class FileFilters {

    private FileFilters() { }

    public static final FileFilter ONLY_DIRECTORIES = new FileFilter() {
        @Override
        public boolean accept(File pathname) {
            return pathname.isDirectory();
        }
    };

    public static final FileFilter ONLY_FILES = new FileFilter() {
        @Override
        public boolean accept(File pathname) {
            return pathname.isFile();
        }
    };

    public static final FileFilter ONLY_XML = filesWithExtension("xml");

    public static FileFilter filesWithExtension(String extension, String... otherExtensions) {
        ImmutableSet.Builder<String> builder = ImmutableSet.builder();
        builder.add(extension);
        builder.add(otherExtensions);
        final ImmutableSet<String> acceptedExtensions = builder.build();

        return new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                if (!pathname.isFile()) {
                    return false;
                }

                String fileExtension = Files.getFileExtension(pathname.getName());
                return acceptedExtensions.contains(fileExtension);
            }
        };
    }

}

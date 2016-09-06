package eu.cyfronoid.core.file;

import java.io.File;

public enum FileExtension {
    xml;

    public boolean matches(File file) {
        return name().equals(FileUtil.getExtension(file));
    }

    public boolean matches(String ext) {
        return name().equals(ext);
    }

    public File formatFileName(File file) {
        if(matches(file)) {
            return file;
        }

        return new File(file.getAbsoluteFile() + "." + name());
    }
}

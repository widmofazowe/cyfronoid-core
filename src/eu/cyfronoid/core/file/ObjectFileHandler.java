package eu.cyfronoid.core.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.apache.log4j.Logger;

import com.google.common.base.Optional;

public class ObjectFileHandler {
    final private static Logger logger = Logger.getLogger(ObjectFileHandler.class);

    public static void saveObjectToFile(File file, Serializable object) {
        try(FileOutputStream fout = new FileOutputStream(file)) {
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(object);
            oos.close();
            logger.debug("Object " + object + " saved to file " + file.getPath());
        } catch(IOException e) {
            logger.error("Cannot read object from file " + file.getPath() + " because of error: " + e);
        }
    }

    public static <T extends Serializable> Optional<T> readObjectFromFile(File file) {
         try(FileInputStream fin = new FileInputStream(file)) {
             ObjectInputStream ois = new ObjectInputStream(fin);
             @SuppressWarnings("unchecked")
             T object = (T) ois.readObject();
             ois.close();
             logger.debug("Object " + object + " read to file " + file.getPath());
             return Optional.of(object);
         } catch(IOException | ClassNotFoundException e) {
             logger.error("Cannot read object from file " + file.getPath() + " because of error: " + e);
             return Optional.absent();
         }
    }

}



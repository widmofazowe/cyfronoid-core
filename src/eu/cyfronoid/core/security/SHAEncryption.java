package eu.cyfronoid.core.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;

import com.sun.org.apache.xml.internal.security.utils.Base64;

import eu.cyfronoid.core.output.Output;

public class SHAEncryption {
    final private static Logger logger = Logger.getLogger(SHAEncryption.class);
    private static MessageDigest sha = null;

    static {
        try {
            sha = MessageDigest.getInstance("SHA");
        } catch(NoSuchAlgorithmException e){
            logger.error("Could not find implementation of SHA algorithm. "+
                "Check if you have Java Cryptography Architecture (JCA) in your Java SDK");
        }
    }

    public static String encrypt(String password){
        if(sha == null) {
            throw new RuntimeException("No SHA implementation. " +
                    "Check if you have Java Cryptography Architecture (JCA) in your Java SDK");
        }

        sha.reset();
        sha.update(password.getBytes());
        return Base64.encode(sha.digest());
    }

    public static void main(String[] args) {
        Output output = new Output();
        output.appendConsole();
        output.println("SHA Encryption:");
        for(String arg : args) {
            output.println(arg + " -> " + SHAEncryption.encrypt(arg));
        }
    }

}



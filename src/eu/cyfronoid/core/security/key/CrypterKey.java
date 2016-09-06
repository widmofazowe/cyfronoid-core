package eu.cyfronoid.core.security.key;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.SecretKeySpec;

import eu.cyfronoid.core.security.Algorithm;

public enum CrypterKey {
    INSTANCE;

    final private static String DIR = "test/configuration/keys/";
    final private static String EXTENSION = "key";

    public String buildFileName(Algorithm algorithm) {
        return DIR + algorithm.name() + "." + EXTENSION;
    }

    public Key createKeyFromString(Algorithm algorithm, String key) {
        String algorithmName = algorithm.name();
        if(algorithmName.contains("DES")) {
            return createKeyForDES(key, algorithmName);
        }
        return new SecretKeySpec(key.getBytes(), 0, algorithm.keySizeInBytes, algorithmName);
    }

    private static Key createKeyForDES(String key, String algorithmName) {
        KeySpec keySpec = null;
        try {
            if ("TripleDES".equals(algorithmName) || ("DESede".equals(algorithmName))) {
                keySpec = new DESedeKeySpec(key.getBytes());
            } else if ("DES".equals(algorithmName)) {
                keySpec = new DESKeySpec(key.getBytes());
            }
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(algorithmName);
            return keyFactory.generateSecret(keySpec);
        } catch (InvalidKeyException | InvalidKeySpecException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}

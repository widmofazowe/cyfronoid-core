package eu.cyfronoid.core.security.key;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.List;
import java.util.Optional;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.log4j.Logger;

import com.google.common.base.Throwables;

import eu.cyfronoid.core.security.Algorithm;

public enum CrypterKeyGenerator {
    INSTANCE;

    final private Logger logger = Logger.getLogger(CrypterKeyGenerator.class);

    public void generateKeys() {
        Algorithm[] supportedCrypts = Algorithm.values();
        for (int i = 0; i < supportedCrypts.length; i++) {
            generateKey(supportedCrypts[i]);
        }
    }

    private SecretKey generateKey(Algorithm algorithm) {
        String algorithmName = algorithm.name();
        SecretKey key = null;
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithmName, "SunJCE");
            keyGenerator.init(algorithm.keySizeInBits);
            key = keyGenerator.generateKey();
            saveKey(key, algorithm);
            logger.info("Key for " + algorithm + " encryption generated.");
        } catch(NoSuchAlgorithmException | NoSuchProviderException e) {
            logger.warn("No such algorithm (" + algorithmName + ") or no such provider available.");
        } catch (IOException e) {
            logger.warn(Throwables.getStackTraceAsString(e));
        }
        return key;
    }

    private void saveKey(SecretKey key, Algorithm algorithm) throws IOException {
        char[] hex = Hex.encodeHex(key.getEncoded());
        writeKeyToFile(algorithm, String.valueOf(hex));
    }

    private void writeKeyToFile(Algorithm algorithm, String key) throws IOException {
        Files.write(Paths.get(CrypterKey.INSTANCE.buildFileName(algorithm)), key.getBytes(), StandardOpenOption.CREATE);
    }

    public Optional<String> loadKey(Algorithm algorithm) throws IOException {
        Optional<String> readKeyFromFile = readKeyFromFile(CrypterKey.INSTANCE.buildFileName(algorithm));
        if(!readKeyFromFile .isPresent()) {
            return Optional.empty();
        }
        byte[] encoded;
        try {
            encoded = Hex.decodeHex(readKeyFromFile.get().toCharArray());
        } catch (DecoderException e) {
            logger.warn(Throwables.getStackTraceAsString(e));
            return null;
        }
        return Optional.of(new String(encoded));
    }

    private Optional<String> readKeyFromFile(String buildFileName) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(buildFileName));
        if(lines.size() > 0) {
            return Optional.of(lines.get(0));
        }

        return Optional.empty();
    }

    public static void main(String[] args) throws Exception {
        CrypterKeyGenerator.INSTANCE.generateKeys();
    }
}

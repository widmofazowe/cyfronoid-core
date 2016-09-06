package eu.cyfronoid.core.security.key;

import java.io.File;
import java.io.FileNotFoundException;
import java.security.Key;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.google.common.collect.ImmutableMap;

import eu.cyfronoid.core.security.Algorithm;

public class DefaultCrypterKeyProvider implements CrypterKeyProvider {
    final private static Logger logger = Logger.getLogger(DefaultCrypterKeyProvider.class);
    private Map<Algorithm, String> keys;

    public DefaultCrypterKeyProvider() {
        loadKeys();
    }

    private void loadKeys() {
        Map<Algorithm, String> keys = new HashMap<>();
        for(Algorithm algorithm : Algorithm.values()) {
            Scanner input;
            String key;
            try {
                input = new Scanner(new File(CrypterKey.INSTANCE.buildFileName(algorithm)));
                key = input.nextLine();
                keys.put(algorithm, key);
            } catch (FileNotFoundException e) {
                logger.warn("Cannot find file with key for " + algorithm.name() + " encryption.");
            }
        }
        this.keys = ImmutableMap.copyOf(keys);
    }

    @Override
    public Key getKey(Algorithm algorithm) {
        if(!keys.containsKey(algorithm)) {
            throw new RuntimeException("There is no key for " + algorithm.name() + " encryption.");
        }

        return CrypterKey.INSTANCE.createKeyFromString(algorithm, keys.get(algorithm));
    }

    @Override
    public boolean hasKey(Algorithm algorithm) {
        return keys.containsKey(algorithm);
    }
}

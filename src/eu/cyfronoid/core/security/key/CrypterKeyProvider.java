package eu.cyfronoid.core.security.key;

import java.security.Key;

import eu.cyfronoid.core.security.Algorithm;

public interface CrypterKeyProvider {
    boolean hasKey(Algorithm algorithm);
    Key getKey(Algorithm algorithm);
}

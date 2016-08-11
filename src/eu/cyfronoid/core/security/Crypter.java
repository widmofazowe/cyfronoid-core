package eu.cyfronoid.core.security;

public interface Crypter {
    String encrypt(String Data) throws Exception;
    String decrypt(String encryptedData) throws Exception;
}



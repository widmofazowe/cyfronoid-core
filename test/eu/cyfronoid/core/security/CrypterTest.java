package eu.cyfronoid.core.security;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

public class CrypterTest {
    final private String KEY = "widmo";
    private String stringToEncrypt;

    @Before
    public void before() {
        stringToEncrypt = "secret pass";
    }

    @Test
    public void testAES() {
        CrypterImpl crypter = new CrypterImpl(KEY, Algorithm.AES);
        try {
            String encrypted = crypter.encrypt(stringToEncrypt);
            assertEquals("Crypter does not work properly.", stringToEncrypt, crypter.decrypt(encrypted));
        } catch (Exception e) {
            fail("Exception in crypter.\n" + e.getMessage());
        }
    }

    @Test
    public void testTripleDES() {
        CrypterImpl crypter = new CrypterImpl(KEY, Algorithm.TripleDES);
        try {
            String encrypted = crypter.encrypt(stringToEncrypt);
            assertEquals("Crypter does not work properly.", stringToEncrypt, crypter.decrypt(encrypted));
        } catch (Exception e) {
            fail("Exception in crypter.\n" + e.getMessage());
        }
    }

}



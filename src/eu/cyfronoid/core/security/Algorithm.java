package eu.cyfronoid.core.security;

public enum Algorithm {
    TripleDES(112),
    Rijndael(128),
    Blowfish,
    DESede(112),
    AES(128),
    DES(56),
    ARCFOUR,
    RC2,
    RC4;

    public final Integer keySizeInBits;
    public final Integer keySizeInBytes;

    private Algorithm() {
        this(128);
    }

    private Algorithm(int size) {
        keySizeInBits = size;
        keySizeInBytes = size/8;
    }
}






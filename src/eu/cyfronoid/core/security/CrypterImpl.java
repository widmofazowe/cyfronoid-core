package eu.cyfronoid.core.security;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.sun.org.apache.xml.internal.security.utils.Base64;

import eu.cyfronoid.core.output.Output;

public class CrypterImpl implements Crypter {
    final private static Logger logger = Logger.getLogger(CrypterImpl.class);
    private String key;
    private Algorithm algorithm;

    public CrypterImpl(String key, Algorithm algorithm) {
        this.key = key;
        this.algorithm = algorithm;
    }

    @Override
    public String encrypt(String data) throws Exception {
        Cipher c = Cipher.getInstance(algorithm.toString());
        c.init(Cipher.ENCRYPT_MODE, generateKey());
        byte[] encVal = c.doFinal(data.getBytes());
        return new BASE64Encoder().encode(encVal);
    }

    @Override
    public String decrypt(String encryptedData) throws Exception {
        Cipher c = Cipher.getInstance(algorithm.toString());
        c.init(Cipher.DECRYPT_MODE, generateKey());
        byte[] decordedValue = new BASE64Decoder().decodeBuffer(encryptedData);
        return new String(c.doFinal(decordedValue));
    }

    private Key generateKey() throws Exception {
        return new SecretKeySpec(key.getBytes(), algorithm.toString());
    }

    public static String[] getSupportedCrypts() {
        Algorithm[] algorithms = Algorithm.values();
        String[] supportedCrypts = new String[algorithms.length];
        for(int i = 0; i < algorithms.length; ++i) {
            supportedCrypts[i] = algorithms[i].toString();
        }
        return supportedCrypts;
        /*
        String serviceType = "Cipher";
        Set<String> result = new HashSet<>();
        Provider provider = Security.getProvider("SunJCE");
        Set<Object> keys = provider.keySet();
        for(Iterator<Object> it = keys.iterator(); it.hasNext(); ) {
            String key = (String)it.next();
            key = key.split(" ")[0];
            if(key.startsWith(serviceType + ".")) {
                if(!key.substring(serviceType.length()+1).startsWith("PBE")) {
                    result.add(key.substring(serviceType.length()+1));
                }
            } else if(key.startsWith("Alg.Alias." + serviceType + ".")) {
                // This is an alias
                if(!key.substring(serviceType.length()+11).startsWith("PBE") && !key.substring(serviceType.length()+11).contains(".")) {
                    result.add(key.substring(serviceType.length()+11));
                }
            }
        }
        return result.toArray(new String[result.size()]);
        */
    }

    public static void generateKeys(Output output) {
        String[] supportedCrypts = getSupportedCrypts();
        for (int i = 0; i < supportedCrypts.length; i++) {
            generateKey(supportedCrypts[i], output);
        }
    }

    public static SecretKey generateKey(String name, Output output) {
        String algorithmName = name;
        if (algorithmName.startsWith("AES")) {
            algorithmName = "AES";
        }
        javax.crypto.KeyGenerator gen = null;
        javax.crypto.SecretKey key = null;
        try {
            gen = javax.crypto.KeyGenerator.getInstance(algorithmName, "SunJCE");
            key = gen.generateKey();
            ByteArrayOutputStream bs = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(bs);
            os.writeObject(key.getEncoded());
            os.flush();
            bs.close();
            String s = new String(Base64.encode(bs.toByteArray()));
            output.println("");
            output.println(name + "\n" + s);
        } catch(NoSuchAlgorithmException | NoSuchProviderException | IOException e) {
            logger.warn("No such algorithm (" + algorithmName + ") or no such provider available.");
        }
        return key;
    }

    public static void printSupportedCrypts() {
        Output output = new Output();
        output.appendConsole();
        String[] supportedCrypts = getSupportedCrypts();
        output.println("Supported crypts:");
        for(int i = 0; i< supportedCrypts.length; i++)	{
            output.println(supportedCrypts[i]);
        }
    }

    public static void main(String[] args) throws Exception {
//        final String testKey = "gGCFTgAgAAeHAAAAAQEs6q8QzI4kQMmAjesA0RWg==";
//        CommandLineParameters clParameters = new CommandLineParameters();
//        clParameters.register(CommandLineArgument.builder().name("encrypt").setDelimiter(":").setParametersNum(1).build());
//        clParameters.register(CommandLineArgument.builder().name("decrypt").setDelimiter(":").setParametersNum(1).build());
//        clParameters.parse(args);
//
//        CrypterImpl ci = new CrypterImpl(testKey, Algorithm.AES);
//        if(clParameters.isAvailable("encrypt")) {
//            System.out.println(ci.encrypt(clParameters.get("encrypt", 0)));
//        }


        //getSupportedCrypts();
        //printSupportedCrypts();
       // generateKeys(new Output().appendConsole());
        /*
        for(Provider provider: Security.getProviders()) {
              System.out.println(provider.getName());
              for (String key: provider.stringPropertyNames())
                System.out.println("\t" + key + "\t" + provider.getProperty(key));
        }*/
    }

}



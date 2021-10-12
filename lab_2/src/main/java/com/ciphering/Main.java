package com.ciphering;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

public class Main {

    static final String certificatePath = "src/main/resources/public.cer";
    static final String privateKeyPath = "src/main/resources/private.p12";

    public static void main(String[] args) {
        // check key size
        Security.setProperty("crypto.policy", "unlimited");
        try {
            int maxKeySize = javax.crypto.Cipher.getMaxAllowedKeyLength("AES");
            System.out.println("Max Key Size for AES : " + maxKeySize);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // common
        Security.addProvider(new BouncyCastleProvider());
        try {
            CertificateFactory certFactory = CertificateFactory.getInstance("X.509", "BC");
            X509Certificate certificate = (X509Certificate) certFactory.generateCertificate(new FileInputStream(certificatePath));
            char[] keystorePassword = "password".toCharArray();
            char[] keyPassword = "password".toCharArray();

            KeyStore keystore = KeyStore.getInstance("PKCS12");
            keystore.load(new FileInputStream(privateKeyPath), keystorePassword);
            PrivateKey privateKey = (PrivateKey) keystore.getKey("baeldung", keyPassword);

            String secretMessage = "My name is Sophie and let's go party";
            System.out.println("Original Message : " + secretMessage);
            System.out.println();

            // 1.1 cipher
            System.out.println("Encrypted data :");
            byte[] stringToEncrypt = secretMessage.getBytes();
            byte[] encryptedData = Crypto.encryptData(stringToEncrypt, certificate);
            String encryptedMessage = new String(encryptedData);
            System.out.println(encryptedMessage);
            System.out.println();

            // 1.2 decipher
            byte[] rawData = Crypto.decryptData(encryptedData, privateKey);
            String decryptedMessage = new String(rawData);
            System.out.println("Decrypted Message : " + decryptedMessage);
            System.out.println();

            // 2.1 signature
            System.out.println("Signed data : ");
            byte[] signedData = Crypto.signData(decryptedMessage, certificate, privateKey);
            System.out.println(new String(signedData));
            System.out.println();

            // 2.2 verification
            boolean verifSignData = Crypto.verifSignData(signedData);
            if (verifSignData) {
                System.out.println("Data verified");
            } else {
                System.out.println("Data not verified");
            }
            System.out.println();

            String someUnsignedData = "S";
            try {
                boolean verifUnSignData = Crypto.verifSignData(someUnsignedData.getBytes());

                if (verifUnSignData) {
                    System.out.println("Data verified");
                } else {
                    System.out.println("Data not verified");
                }
            } catch (Exception e) {
                System.out.println("Data not verified");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}

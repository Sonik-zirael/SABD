package com.ciphering;

import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import org.bouncycastle.cms.CMSException;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.OperatorCreationException;
import org.junit.Test;

public class CryptoTest {
    String certificatePath = "src/test/resources/public.cer";
    String privateKeyPath = "src/test/resources/private.p12";
    char[] p12Password = "password".toCharArray();
    char[] keyPassword = "password".toCharArray();

    @Test
    public void givenCryptographicResource_whenOperationSuccess_returnTrue() throws CertificateException, NoSuchProviderException, NoSuchAlgorithmException, IOException, KeyStoreException, UnrecoverableKeyException, CMSException, OperatorCreationException {
        Security.addProvider(new BouncyCastleProvider());

        CertificateFactory certFactory = CertificateFactory.getInstance("X.509", "BC");
        X509Certificate certificate = (X509Certificate) certFactory.generateCertificate(new FileInputStream(certificatePath));
        KeyStore keystore = KeyStore.getInstance("PKCS12");
        keystore.load(new FileInputStream(privateKeyPath), p12Password);
        PrivateKey privateKey = (PrivateKey) keystore.getKey("baeldung", keyPassword);
        String secretMessage = "My password is 123456Seven";
        System.out.println("Original Message : " + secretMessage);
        byte[] stringToEncrypt = secretMessage.getBytes();
        byte[] encryptedData = Crypto.encryptData(stringToEncrypt, certificate);
        byte[] rawData = Crypto.decryptData(encryptedData, privateKey);
        String decryptedMessage = new String(rawData);
        assertTrue(decryptedMessage.equals(secretMessage));
        byte[] signedData = Crypto.signData(decryptedMessage, certificate, privateKey);
        Boolean check = Crypto.verifSignData(signedData);
        assertTrue(check);
    }

}
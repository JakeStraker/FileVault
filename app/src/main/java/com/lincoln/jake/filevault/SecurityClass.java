package com.lincoln.jake.filevault;

import android.util.Base64;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by JakeS on 07/12/2015.
 */
public class SecurityClass {

    private static final String ALGORITHM = "AES";
    private static byte[] keyValue = null;

    public static String encrypt(String valueToEnc, byte[] inputkey) throws Exception { //encryption mechanism
        keyValue = inputkey;
        Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGORITHM);
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encValue = c.doFinal(valueToEnc.getBytes());
        String encryptedValue = Base64.encodeToString(encValue, Base64.DEFAULT);
        keyValue = null;
        return encryptedValue;
        }

    public static String decrypt(String encryptedValue, byte[] inputkey) throws Exception { //decrpytion mechanism
        keyValue = inputkey;
        Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGORITHM);
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decodedValue = Base64.decode(encryptedValue, Base64.DEFAULT);
        byte[] decValue = c.doFinal(decodedValue);
        String decryptedValue = new String(decValue);
        keyValue = null;
        return decryptedValue;
        }

    private static Key generateKey() throws Exception { //generate cipher into key for algorithms
        Key key = new SecretKeySpec(keyValue, ALGORITHM);
        return key;
        }
}

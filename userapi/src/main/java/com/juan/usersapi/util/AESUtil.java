package com.juan.usersapi.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AESUtil {

    private static final String SECRET_KEY = "12345678901234567890123456789012";

    public static String encrypt(String strToEncrypt) {
        try {
            SecretKeySpec secretKey =
                    new SecretKeySpec(SECRET_KEY.getBytes(), "AES");

            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            return Base64.getEncoder()
                    .encodeToString(cipher.doFinal(strToEncrypt.getBytes()));

        } catch (Exception e) {
            throw new RuntimeException("Error while encrypting");
        }
    }

    public static String decrypt(String strToDecrypt) {
        try {
            SecretKeySpec secretKey =
                    new SecretKeySpec(SECRET_KEY.getBytes(), "AES");

            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            return new String(cipher.doFinal(
                    Base64.getDecoder().decode(strToDecrypt)));

        } catch (Exception e) {
            throw new RuntimeException("Error while decrypting");
        }
    }
}

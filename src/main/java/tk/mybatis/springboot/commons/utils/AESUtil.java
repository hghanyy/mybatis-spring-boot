package tk.mybatis.springboot.commons.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.SecureRandom;

public class AESUtil
{
    private static final String SECURE_KEY = "SHA1PRNG";
    private static final String KEY_ALGORITHM = "AES";
    private static final String CIPHER_ALGORITHM_ECB = "AES/ECB/PKCS5Padding";

    public static String encrypt(String src, String key)
            throws Exception
    {
        if (StringUtil.isEmpty(src)) {
            return src;
        }
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(key.getBytes());
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128, random);
        SecretKey secretKey = kgen.generateKey();

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(1, secretKey);
        byte[] encrypt = cipher.doFinal(src.getBytes());

        return Base64.encodeBase64String(encrypt);
    }

    public static String decrypt(String src, String key)
            throws Exception
    {
        if (StringUtil.isEmpty(src)) {
            return src;
        }
        if (!Base64.isBase64(src)) {
            return src;
        }
        byte[] bytesrc = Base64.decodeBase64(src);

        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(key.getBytes());
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128, random);
        SecretKey secretKey = kgen.generateKey();

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(2, secretKey);
        byte[] retByte = cipher.doFinal(bytesrc);

        return new String(retByte);
    }
}

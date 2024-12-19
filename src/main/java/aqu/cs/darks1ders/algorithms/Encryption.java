package aqu.cs.darks1ders.algorithms;

import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

public class Encryption {
    private String PASSWORD = "1zQ^2eK!3mQ^9oF";
    private byte[] SALT = {77, 4, 97, 38, 80, 62, 41, 115};
    
    public String dec(String property) {
        try {
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
            SecretKey key = keyFactory.generateSecret(new PBEKeySpec(PASSWORD.toCharArray()));
            Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
            pbeCipher.init(2, key, new PBEParameterSpec(SALT, 20));
            return new String(pbeCipher.doFinal(base64Decode(property)), "UTF-8");
        } catch (Exception c) {
            System.out.println(c);
        }
        return null;
    }
    
    private byte[] base64Decode(String property) {
        try {
            return (new BASE64Decoder()).decodeBuffer(property);
        } catch (Exception c) {
            System.out.println(c);
        }
        return null;
    }
    public String enc(String property) {
        try {
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
            SecretKey key = keyFactory.generateSecret(new PBEKeySpec(PASSWORD.toCharArray()));
            Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
            pbeCipher.init(1, key, new PBEParameterSpec(SALT, 20));
            return base64Encode(pbeCipher.doFinal(property.getBytes("UTF-8")));
        } catch (Exception c) {
            System.out.println(c);
        }
        return null;
    }
    private String base64Encode(byte[] bytes) {
        return (new BASE64Encoder()).encode(bytes);
    }
}
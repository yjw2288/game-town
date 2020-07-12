package com.gametown.account.enc;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

@AllArgsConstructor
public class AES256Machine {

    private final String key;
    private final String iv;

    // 암호화
    @SneakyThrows
    public String aesEncode(Object o) {
        String str = o.toString();
        byte[] keyData = key.getBytes();

        SecretKey secureKey = new SecretKeySpec(keyData, "AES");

        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
        c.init(Cipher.ENCRYPT_MODE, secureKey, new IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8)));

        byte[] encrypted = c.doFinal(str.getBytes(StandardCharsets.UTF_8));
        return new String(Base64.encodeBase64(encrypted));
    }

    // 복호화
    @SneakyThrows
    public String aesDecode(String msg) {
        byte[] keyData = key.getBytes();
        SecretKey secureKey = new SecretKeySpec(keyData, "AES");
        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
        c.init(Cipher.DECRYPT_MODE, secureKey, new IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8)));
        byte[] byteStr = Base64.decodeBase64(msg.getBytes(StandardCharsets.UTF_8));

        return new String(c.doFinal(byteStr), StandardCharsets.UTF_8);
    }
}

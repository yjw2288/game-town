package com.gametown.account.enc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

@Slf4j
@Component
public class SHA2Machine {
    public String getSHA256(String input) {
        String toReturn = null;
        try {
            String salt = "G_T_S";

            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.reset();
            digest.update(salt.getBytes(StandardCharsets.UTF_8));
            digest.update(input.getBytes(StandardCharsets.UTF_8));
            return String.format("%064x", new BigInteger(1, digest.digest()));
        } catch (Exception e) {
            log.error("getSha256 Error", e);
            throw new RuntimeException("password error");
        }
    }
}

package com.gametown.account.config;

import com.gametown.account.enc.AES256Machine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AesConfig {
    @Bean
    public AES256Machine aes256Machine() {
        return new AES256Machine("sdjfkjsaklfjkwljfklwjlkfjwkejfljkljklwe");
    }
}

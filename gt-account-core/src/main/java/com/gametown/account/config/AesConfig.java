package com.gametown.account.config;

import com.gametown.account.enc.AES256Machine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AesConfig {
    @Bean
    public AES256Machine aes256Machine() {
        return new AES256Machine("1fh8128hf892yc123y981ynv1892fnph", "dsjflajjwoiehf;N");
    }
}

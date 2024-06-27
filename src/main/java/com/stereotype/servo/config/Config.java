package com.stereotype.servo.config;

import org.jline.utils.AttributedString;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.shell.jline.PromptProvider;

@Configuration
public class Config {
    @Bean
    public PromptProvider customPromptProvider() {
        return () -> new AttributedString("servo:> ");
    }
}

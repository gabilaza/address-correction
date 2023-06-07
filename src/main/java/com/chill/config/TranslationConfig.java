package com.chill.config;


import com.chill.normalize.Translation;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class TranslationConfig {

    @Bean
    public Translation translation() {
        return new Translation();
    }
}
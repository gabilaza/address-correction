package com.chill.config;

import com.chill.normalize.Spellchecker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpellCheckerConfig {
    @Value("${app.spellchecker.dictionary.path}")
    private String dictionaryPath;

    @Bean
    public Spellchecker spellchecker() {
        return new Spellchecker(dictionaryPath);
    }
}

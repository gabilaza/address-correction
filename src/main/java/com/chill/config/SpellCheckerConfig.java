package com.chill.config;

import com.chill.normalize.Spellchecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class SpellCheckerConfig {
    private static final Logger logs = LoggerFactory.getLogger(SpellCheckerConfig.class);

    @Value("${app.spellchecker.dictionary.path}")
    private String dictionaryPath;
    @Value("${app.spellchecker.dictionary.workDir}")
    private String dictionaryWorkDir;
    @Value("${app.spellchecker.suggestion.number}")
    private int suggestionNumber;
    @Value("${app.spellchecker.suggestion.accuracy}")
    private float suggestionAccuracy;

    @Bean
    public Spellchecker spellchecker() {
        try {
            return new Spellchecker(dictionaryPath, dictionaryWorkDir, suggestionNumber, suggestionAccuracy);
        } catch (IOException exception) {
            logs.error(exception.getMessage());
            return null;
        }
    }
}

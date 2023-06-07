package com.chill.normalize;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Translation {
    private final Map<Language, Map<String, String>> translation;

    public Translation() {
        this.translation = new HashMap<>();
    }

    public void initTranslation(Map<Language, List<String>> translationMap) {
        for (Language language : Language.values()) {
            Map<String, String> translationLanguage = new HashMap<>();

            for (int i = 0; i < translationMap.get(language).size(); i++) {
                for (Language lang : Language.values()) {
                    translationLanguage.put(translationMap.get(lang).get(i), translationMap.get(language).get(i));
                }
            }

            this.translation.put(language, translationLanguage);
        }
    }

    public String translate(String word, Language language) {
        return Optional.ofNullable(this.translation.get(language).get(word)).orElse(word);
    }
}

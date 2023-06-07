package com.chill.normalize;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * A class for translating words between different languages.
 * It contains a map of languages to
 * maps, where each sub-map is a mapping from words in different languages to their translations in
 * the parent language.
 */
public class Translation {
    private final Map<Language, Map<String, String>> translation;

    public Translation() {
        this.translation = new HashMap<>();
    }

    /**
     * Initializes the translation map with provided translations.
     * For each language, it creates a
     * sub-map where each word from all the languages maps to its translation in this language.
     *
     * @param translationMap a map from languages to lists of words, where each list contains
     *     translations of the same word in different languages
     */
    public void initTranslation(Map<Language, List<String>> translationMap) {
        for (Language language : Language.values()) {
            Map<String, String> translationLanguage = new HashMap<>();

            for (int i = 0; i < translationMap.get(language).size(); i++) {
                for (Language lang : Language.values()) {
                    translationLanguage.put(
                            translationMap.get(lang).get(i), translationMap.get(language).get(i));
                }
            }

            this.translation.put(language, translationLanguage);
        }
    }
    /**
     * Translates a given word to a specified language.
     * If the word doesn't exist in the translation
     * map or there's no translation for this word in the specified language, the original word is
     * returned.
     *
     * @param word the word to be translated
     * @param language the language to translate the word into
     * @return the translated word or the original word if the translation doesn't exist
     */
    public String translate(String word, Language language) {
        return Optional.ofNullable(this.translation.get(language).get(word)).orElse(word);
    }
}

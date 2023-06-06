package com.chill.normalize;

import com.chill.service.TreeAddressService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
/**
 * The Translation class is responsible for managing and providing translations for different words.
 * It leverages a TreeAddressService to determine the existence of a vertex by location, and uses
 * this information to populate a translation map.
 */
@Component
@RequiredArgsConstructor
public class Translation {
    private final HashMap<String, String> translationMap = new HashMap<>();

    private final TreeAddressService treeAddressService;

    public HashMap<String, String> getTranslationMap() {
        return translationMap;
    }
    /**
     * Initializes the translation map with a list of word lists.
     * It finds the word in each list that exists as a vertex location in the treeAddressService.
     * This word is then used as the translation for all the words in the list.
     * If no such word exists, the list is skipped.
     *
     * @param words a list of word lists, where each word list represents a group of translations.
     */
    public void initTranslate(List<List<String>> words) {
        for (List<String> wordList : words) {
            String valueKey = null;
            for (String word : wordList) {
                if (treeAddressService.existsVertexByLocation(word)) {
                    valueKey = word;
                    break;
                }
            }

            if (valueKey != null) {
                for (String word : wordList) {
                    translationMap.put(word, valueKey);
                }
            }
        }
    }
}

package com.chill.normalize;

import com.swabunga.spell.engine.SpellDictionaryHashMap;
import com.swabunga.spell.engine.Word;
import com.swabunga.spell.event.SpellChecker;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

public class Spellchecker {

    File dictionary;

    SpellDictionaryHashMap dictionaryHashMap;
    SpellChecker spellChecker;

    public Spellchecker(String dictionaryPath) {
        try {
            dictionary = new File(dictionaryPath);
            dictionaryHashMap = new SpellDictionaryHashMap(dictionary);
            spellChecker = new SpellChecker(dictionaryHashMap);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public String normalize(String word) {
        return word.replaceAll("[^\\p{L}\\p{N}\\p{M} ]", "");

    }

    public String spellcheck(String text) {
        StringTokenizer stringTokenizer = new StringTokenizer(text, " ");
        StringBuilder stringBuilder = new StringBuilder();
        while (stringTokenizer.hasMoreTokens()) {
            String word = stringTokenizer.nextToken();
            if (!spellChecker.isCorrect(word)) {
                List<?> rawSuggestions = spellChecker.getSuggestions(word, 0);
                if (!rawSuggestions.isEmpty()) {
                    Word suggestedWord = (Word) rawSuggestions.get(0);
                    stringBuilder.append(suggestedWord.getWord());
                }
            } else {
                stringBuilder.append(word);
            }
            if (stringTokenizer.hasMoreTokens()) {
                stringBuilder.append(" ");
            }
        }
        return stringBuilder.toString();
    }
}

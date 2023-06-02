package com.chill.normalize;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.spell.PlainTextDictionary;
import org.apache.lucene.search.spell.SpellChecker;
import org.apache.lucene.store.FSDirectory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

public class Spellchecker {

    private final int suggestionsNumber;

    private final float accuracy;

    private final org.apache.lucene.search.spell.SpellChecker spellChecker;

    public Spellchecker(String dictionaryPath, String dictionaryWorkDir, int suggestionsNumber, float accuracy) {
        try {
            FSDirectory dir = FSDirectory.open(Paths.get(dictionaryWorkDir));
            File dictionary = new File(dictionaryPath);
            spellChecker = new SpellChecker(dir);
            this.suggestionsNumber = suggestionsNumber;
            this.accuracy = accuracy;
            try {
                spellChecker.indexDictionary(new PlainTextDictionary(dictionary.toPath()), new IndexWriterConfig(new StandardAnalyzer()), true);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String normalize(String word) {
        return word.replaceAll("[^\\p{L}\\p{N}\\p{M} ]", "");
    }

    public List<String> spellcheck(String text) {
        StringTokenizer tokenizer = new StringTokenizer(text, " ");
        List<String> suggestions = new LinkedList<>();
        while (tokenizer.hasMoreTokens()) {
            String word = tokenizer.nextToken();
            try {
                if (!spellChecker.exist(word)) {
                    String[] rawSuggestions = spellChecker.suggestSimilar(word, suggestionsNumber, accuracy);
                    suggestions.addAll(List.of(rawSuggestions));
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return suggestions;
    }
}
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

/**
 * Spellchecker class is a utility for providing spell checking and suggestions for corrections of a
 * given text based on a dictionary.
 * It leverages Apache Lucene's SpellChecker for functionality.
 */
public class Spellchecker {
    private final int suggestionsNumber;

    private final float accuracy;

    private final SpellChecker spellChecker;

    /**
     * Creates a new instance of Spellchecker with a given dictionary and configuration.
     *
     * @param dictionaryPath the path to the dictionary file.
     * @param dictionaryWorkDir the path to the working directory for the dictionary.
     * @param suggestionsNumber the number of suggestions to provide for a given word.
     * @param accuracy the accuracy of the suggestions, as a float.
     * @throws IOException If there is an issue reading from the dictionary file or directory.
     */
    public Spellchecker(
            String dictionaryPath, String dictionaryWorkDir, int suggestionsNumber, float accuracy)
            throws IOException {
        FSDirectory dir = FSDirectory.open(Paths.get(dictionaryWorkDir));
        File dictionary = new File(dictionaryPath);
        spellChecker = new SpellChecker(dir);
        this.suggestionsNumber = suggestionsNumber;
        this.accuracy = accuracy;
        spellChecker.indexDictionary(
                new PlainTextDictionary(dictionary.toPath()),
                new IndexWriterConfig(new StandardAnalyzer()),
                true);
    }

    /**
     * Normalizes a given word by removing any characters that are not letters, numbers or
     * diacritical marks.
     *
     * @param word the word to normalize.
     * @return the normalized word.
     */
    public String normalize(String word) {
        return word.replaceAll("[^\\p{L}\\p{N}\\p{M} ]", "");
    }

    /**
     * Splits the given text into words and provides suggestions for each word if it is not in the
     * dictionary.
     * If a word exists in the dictionary, it is simply added to the suggestion list.
     * For a word not in the dictionary, the method suggests similar words based on the configured
     * suggestions number and accuracy.
     *
     * @param text the text to spellcheck.
     * @return a list of suggested corrections for each word in the text.
     */
    public List<String> spellcheck(String text) {
        StringTokenizer tokenizer = new StringTokenizer(text, " ");
        List<String> suggestions = new LinkedList<>();
        while (tokenizer.hasMoreTokens()) {
            String word = tokenizer.nextToken();
            suggestions.add(word);
            try {
                if (!spellChecker.exist(word)) {
                    String[] rawSuggestions =
                            spellChecker.suggestSimilar(word, suggestionsNumber, accuracy);
                    suggestions.addAll(List.of(rawSuggestions));
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return suggestions;
    }
}

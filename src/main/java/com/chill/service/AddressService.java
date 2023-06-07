package com.chill.service;

import com.chill.entity.Address;
import com.chill.graph.Chain;
import com.chill.graph.Vertex;
import com.chill.mapper.AddressMapper;
import com.chill.normalize.Language;
import com.chill.normalize.Spellchecker;
import com.chill.normalize.Translation;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressMapper addressMapper;

    private final Spellchecker spellchecker;

    private final TreeAddressService treeAddressService;

    private final Translation translation;

    @Value("${app.normalize.suggestion.maxConcatTimes}")
    private int maxConcatTimes;

    public Address correctAddress(Iterable<String> locations) {
        Set<Vertex<String>> vertices = treeAddressService.getAllVertices(locations);
        List<Chain<Vertex<String>>> chains = treeAddressService.getAllChains(vertices);
        treeAddressService.computeChainsScores(chains, vertices);

        Collections.sort(chains);

        if (chains.isEmpty()) {
            return new Address();
        }
        return addressMapper.mapToAddress(chains.get(0));
    }

    /**
     * Normalizes an Address object to a list of normalized suggestions.
     * It first maps the address
     * to a string, then normalizes it, and finally provides spelling-checked suggestions for the
     * normalized address string.
     *
     * @param address the Address object to normalize
     * @return a list of string suggestions for the normalized address
     */
    public List<String> normalizeAddress(Address address) {
        String addressStr = addressMapper.mapToString(address);
        addressStr = spellchecker.normalize(addressStr);

        return provideSuggestions(spellchecker.spellcheck(addressStr));
    }

    /**
     * Provides a list of possible suggestions for a given list of words.
     * The method first generates
     * permutations of the provided suggestions, up to a specified maximum number of concatenations.
     * If any concatenated permutation is a valid vertex location, according to the
     * treeAddressService, it is added to the list of results.
     * After the permutation generation, the
     * original suggestions are also appended to the list.
     *
     * <p>Following this, each word in the resultant list is cross-referenced with the
     * translationMap.
     * If a match is found, the word is replaced in the list with its corresponding
     * mapped value from the translationMap.
     *
     * @param suggestions the list of words to generate suggestions from
     * @return a list of string suggestions, including permutations and translations
     */
    public List<String> provideSuggestions(List<String> suggestions) {
        List<String> resultList = new ArrayList<>();
        for (int concatTimes = 1; concatTimes <= maxConcatTimes; concatTimes++) {
            generatePermutations(suggestions, concatTimes, new Stack<>(), resultList);
        }
        resultList.addAll(suggestions);

        resultList.replaceAll(s -> translation.translate(s, Language.ENGLISH));

        Set<String> set = new LinkedHashSet<>(resultList);
        resultList.clear();
        resultList.addAll(set);

        return resultList;
    }

    /**
     * Recursively generates all permutations of a list of words up to a specified maximum number of
     * concatenations.
     * It checks if the concatenated words exist as a vertex location in the
     * treeAddressService, and if so, adds them to the list of results.
     *
     * @param suggestions the list of words to generate permutations from
     * @param concatTimes the maximum number of concatenations
     * @param current the current permutation being built
     * @param resultList the list of resulting permutations
     */
    private void generatePermutations(
            List<String> suggestions,
            int concatTimes,
            Stack<String> current,
            List<String> resultList) {
        if (current.size() == concatTimes) {
            String concatenatedWord = String.join(" ", current);
            if (treeAddressService.existsVertexByLocation(concatenatedWord)) {
                resultList.add(concatenatedWord);
            }
        } else {
            for (int i = 0; i < suggestions.size(); i++) {
                current.push(suggestions.get(i));
                generatePermutations(suggestions, concatTimes, current, resultList);
                current.pop();
            }
        }
    }
}

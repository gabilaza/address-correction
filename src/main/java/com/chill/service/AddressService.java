package com.chill.service;

import com.chill.entity.Address;
import com.chill.graph.Chain;
import com.chill.graph.Vertex;
import com.chill.mapper.AddressMapper;
import com.chill.normalize.Spellchecker;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.Stack;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressMapper addressMapper;

    private final Spellchecker spellchecker;

    private final TreeAddressService treeAddressService;

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

    public List<String> normalizeAddress(Address address) {
        String addressStr = addressMapper.mapToString(address);
        addressStr = spellchecker.normalize(addressStr);

        return provideSuggestions(spellchecker.spellcheck(addressStr));
    }

    public List<String> provideSuggestions(List<String> suggestions) {
        List<String> resultList = new ArrayList<>();
        for (int concatTimes = 1; concatTimes <= maxConcatTimes; concatTimes++) {
            generatePermutations(suggestions, concatTimes, new Stack<>(), resultList);
        }

        return resultList;
    }

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

package com.chill.service;

import com.chill.entity.Address;
import com.chill.graph.Vertex;
import com.chill.graph.Chain;

import com.chill.mapper.AddressMapper;
import com.chill.normalize.Spellchecker;
import com.chill.graph.TreeAddress;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.LinkedList;
import java.util.Set;
import java.util.Collections;
import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class AddressService {
    private static final Logger logs = LoggerFactory.getLogger(AddressService.class);

    private final AddressMapper addressMapper;

    private final Spellchecker spellchecker;

    private final TreeAddress treeAddress;

    public Address correctAddress(List<String> suggestionList) {
        List<Chain<Vertex<String>>> chains = new LinkedList<>();
        Set<Vertex<String>> vertices = new HashSet<>();

        for (String suggestion : suggestionList) {
            List<Vertex<String>> suggestionVertices = treeAddress.getVerticesByString(suggestion);
            if (suggestionVertices == null) {
                logs.error("This suggestion not found in Tree: " + suggestion);
            }
            else {
                vertices.addAll(suggestionVertices);
                for (Vertex<String> vertex : suggestionVertices) {
                    chains.add(treeAddress.getChainFromVertexToRoot(vertex));
                }
            }
        }

        for (Chain<Vertex<String>> chain : chains) {
            chain.computeScore(vertices);
        }

        Collections.sort(chains);

        if (chains.isEmpty())
            return new Address();
        return addressMapper.mapToAddress(chains.get(0));
    }

    public List<String> normalizeAddress(Address address) {
        String addressStr = addressMapper.mapToString(address);
        addressStr = spellchecker.normalize(addressStr);

        return spellchecker.spellcheck(addressStr);
    }
}

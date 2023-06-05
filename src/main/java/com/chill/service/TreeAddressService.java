package com.chill.service;

import com.chill.graph.Chain;
import com.chill.graph.TreeAddress;
import com.chill.graph.Vertex;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TreeAddressService {
    private static final Logger logs = LoggerFactory.getLogger(TreeAddressService.class);

    public final TreeAddress treeAddress;

    public List<Chain<Vertex<String>>> getAllChains(Iterable<Vertex<String>> vertices) {
        List<Chain<Vertex<String>>> chains = new LinkedList<>();

        for (Vertex<String> vertex : vertices) {
            chains.add(treeAddress.getChain(vertex));
        }

        return chains;
    }

    public Set<Vertex<String>> getAllVertices(Iterable<String> locations) {
        Set<Vertex<String>> vertices = new HashSet<>();

        for (String location : locations) {
            List<Vertex<String>> locationVertices = treeAddress.getVerticesByLocation(location);
            if (locationVertices.isEmpty()) {
                logs.error("This location not found in Tree: " + location);
            } else {
                vertices.addAll(locationVertices);
            }
        }

        return vertices;
    }

    public void computeChainsScores(
            List<Chain<Vertex<String>>> chains, Set<Vertex<String>> vertices) {
        for (Chain<Vertex<String>> chain : chains) {
            computeChainScore(chain, vertices);
        }
    }

    private void computeChainScore(Chain<Vertex<String>> chain, Set<Vertex<String>> vertices) {
        int factorScore = chain.size();
        for (Vertex<String> vertex : chain) {
            if (vertices.contains(vertex)) {
                chain.addScore(factorScore);
            }
            factorScore -= 1;
        }
    }

    public boolean existsVertexByLocation(String location) {
        return treeAddress.existsVertexByLocation(location);
    }
}

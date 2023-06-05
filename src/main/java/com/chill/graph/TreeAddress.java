package com.chill.graph;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class TreeAddress {
    private final Map<String, List<Vertex<String>>> verticesMap;

    private TreeState currentTreeState;

    public TreeAddress() {
        this.verticesMap = new HashMap<>();
        this.currentTreeState = TreeState.INITIALIZED;
    }

    public void initTree(List<Vertex<String>> roots) {
        if (this.currentTreeState != TreeState.INITIALIZED) {
            throw new IllegalStateException(
                    "Cannot run initTree() in state " + this.currentTreeState);
        }

        Consumer<Vertex<String>> consumer =
                node -> {
                    if (!this.verticesMap.containsKey(node.element())) {
                        this.verticesMap.put(node.element(), new LinkedList<>());
                    }
                    this.verticesMap.get(node.element()).add(node);
                };

        for (Vertex<String> root : roots) {
            dfs(root, consumer);
        }

        this.currentTreeState = TreeState.READY;
    }

    public void destroyTree() {
        this.currentTreeState = TreeState.DESTROYED;
    }

    public List<Vertex<String>> getVerticesByLocation(String location) {
        if (this.currentTreeState != TreeState.READY) {
            throw new IllegalStateException(
                    "Cannot run getVerticesByLocation() in state " + this.currentTreeState);
        }

        if (existsVertexByLocation(location)) {
            return this.verticesMap.get(location);
        }
        return new LinkedList<>();
    }

    public boolean existsVertexByLocation(String location) {
        if (this.currentTreeState != TreeState.READY) {
            throw new IllegalStateException(
                    "Cannot run existsVertexByLocation() in state " + this.currentTreeState);
        }

        return this.verticesMap.containsKey(location);
    }

    public Chain<Vertex<String>> getChain(Vertex<String> vertex) {
        if (this.currentTreeState != TreeState.READY) {
            throw new IllegalStateException(
                    "Cannot run getChainFromVertexToRoot() in state " + this.currentTreeState);
        }

        Chain<Vertex<String>> chain = new Chain<>();
        Consumer<Vertex<String>> consumer = chain::addElement;
        walkToRoot(vertex, consumer);

        return chain;
    }

    private void dfs(Vertex<String> root, Consumer<Vertex<String>> consumer) {
        if (root != null) {
            consumer.accept(root);
            for (Vertex<String> child : root.getChildren()) {
                dfs(child, consumer);
            }
        }
    }

    private void walkToRoot(Vertex<String> vertex, Consumer<Vertex<String>> consumer) {
        if (vertex != null) {
            consumer.accept(vertex);
            walkToRoot(vertex.getParent(), consumer);
        }
    }
}

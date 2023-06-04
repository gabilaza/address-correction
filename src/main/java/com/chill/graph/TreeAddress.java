package com.chill.graph;

import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.function.Consumer;
import java.util.HashMap;

public class TreeAddress {
    private Map<String, List<Vertex<String>>> verticesMap;

    private TreeState currentTreeState;

    public TreeAddress() {
        this.verticesMap = new HashMap<>();
        this.currentTreeState = TreeState.INITIALIZED;
    }

    public synchronized void initTree(List<Vertex<String>> roots) {
        if (this.currentTreeState != TreeState.INITIALIZED)
            throw new IllegalStateException("Cannot run initTree() in state " + this.currentTreeState);

        Consumer<Vertex<String>> consumer = node -> {
            if (!this.verticesMap.containsKey(node.element())) {
                this.verticesMap.put(node.element(), new LinkedList<Vertex<String>>());
            }
            this.verticesMap.get(node.element()).add(node);
        };

        for (Vertex<String> root : roots) {
            dfs(root, consumer);
        }

        this.currentTreeState = TreeState.READY;
    }

    public synchronized void destroyTree() {
        this.currentTreeState = TreeState.DESTROYED;
    }

    public List<Vertex<String>> getVerticesByString(String string) {
        if (this.currentTreeState != TreeState.READY)
            throw new IllegalStateException("Cannot run getVerticesByString() in state " + this.currentTreeState);

        return this.verticesMap.get(string);
    }

    public Chain<Vertex<String>> getChainFromVertexToRoot(Vertex<String> vertex) {
        if (this.currentTreeState != TreeState.READY)
            throw new IllegalStateException("Cannot run getChainFromVertexToRoot() in state " + this.currentTreeState);

        Chain<Vertex<String>> chain = new Chain<Vertex<String>>();

        Consumer<Vertex<String>> consumer = node -> chain.addVertex(node);

        getRoot(vertex, consumer);

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

    private void getRoot(Vertex<String> vertex, Consumer<Vertex<String>> consumer) {
        if (vertex != null) {
            consumer.accept(vertex);

            getRoot(vertex.getParent(), consumer);
        }
    }
}

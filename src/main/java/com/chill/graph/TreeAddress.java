package com.chill.graph;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Represents a tree data structure used for geographical addressing.
 * The tree's nodes are vertices
 * representing various geographical units (like country, state, city, and postal code), and the
 * tree has different states based on its current operation.
 */
public class TreeAddress {
    private final Map<String, List<Vertex<String>>> verticesMap;

    private TreeState currentTreeState;


    public TreeAddress() {
        this.verticesMap = new HashMap<>();
        this.currentTreeState = TreeState.INITIALIZED;
    }

    /**
     * Initializes the tree using a list of root vertices.
     * It also checks the current state of
     * the tree, throwing an exception if the method is invoked in an inappropriate state.
     *
     * @param roots the root vertices to initialize the tree
     * @throws IllegalStateException if the method is invoked when the tree is not in the
     *                               INITIALIZED state
     */
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

    /**
     * Returns a list of vertices in the tree that correspond to a given location.
     * Checks the current state of the tree
     * and throws an exception if the tree is not in the READY state.
     *
     * @param location the location for which to find vertices
     * @return a list of vertices corresponding to the given location
     * @throws IllegalStateException if the method is invoked when the tree is not in the
     *                               READY state
     */
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

    /**
     * Checks if a vertex corresponding to a given location exists in the tree.
     * Checks the current state of the tree
     * and throws an exception if the tree is not in the READY state.
     *
     * @param location the location for which to check the existence of a vertex
     * @return true if a vertex for the location exists, false otherwise
     * @throws IllegalStateException if the method is invoked when the tree is not in the
     *                               READY state
     */
    public boolean existsVertexByLocation(String location) {
        if (this.currentTreeState != TreeState.READY) {
            throw new IllegalStateException(
                    "Cannot run existsVertexByLocation() in state " + this.currentTreeState);
        }

        return this.verticesMap.containsKey(location);
    }

    /**
     * Returns a chain of vertices from a given vertex to the root of the tree.
     * Checks the current state of the tree
     * and throws an exception if the tree is not in the READY state.
     *
     * @param vertex the vertex from which the chain should start
     * @return the chain of vertices from the given vertex to the root
     * @throws IllegalStateException if the method is invoked when the tree is not in the
     *                               READY state
     */
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

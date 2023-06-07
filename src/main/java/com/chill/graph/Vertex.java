package com.chill.graph;

/**
 * An interface representing a vertex in a tree structure, specifically in the context of
 * geographical addressing hierarchy.
 * The vertex represents the geographical units
 * City, State, Country and PostalCode.
 * Each vertex has a parent (unless it is the root of the tree)
 * and zero or more children, forming a tree-like structure.
 *
 * @param <T> the type of the element contained within the vertex, representing the geographical unit.
 */
public interface Vertex<T> {

    /**
     * Retrieves the element contained within this vertex.
     * The element can be a geographical
     * entity such as a City, State, Country or PostalCode.
     *
     * @return the element contained within this vertex.
     */
    T element();

    /**
     * Retrieves the parent of this vertex in the tree.
     * The parent is the geographical unit
     * immediately above this one in the hierarchy.
     * For example, if this vertex represents a city,
     * the parent would be the state the city is in.
     *
     * @return the parent vertex of this vertex,
     * or null if this vertex is the root of the tree.
     */
    Vertex<T> getParent();

    /**
     * Retrieves the children of this vertex in the tree.
     * The children are the geographical units
     * immediately below this one in the hierarchy.
     * For example, if this vertex represents a country,
     * the children would be the states within the country.
     *
     * @return an iterable collection of child vertices of this vertex.
     * If this vertex has no children, the method returns an empty iterable.
     */
    Iterable<Vertex<T>> getChildren();
}

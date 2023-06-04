package com.chill.graph;

public interface Vertex<T> {
    T element();

    Vertex<T> getParent();

    Iterable<Vertex<T>> getChildren();
}

package com.chill.graph;

import lombok.Data;

import java.util.List;
import java.util.LinkedList;
import java.util.Set;

@Data
public class Chain<T> implements Comparable<Chain<T>> {
    private int score;
    private List<T> chain;

    public Chain() {
        this.score = 0;
        this.chain = new LinkedList<>();
    }

    private void incScore() {
        this.score += 1;
    }

    public void addVertex(T vertex) {
        this.chain.add(vertex);
    }

    public void computeScore(Set<T> vertices) {
        for (T vertex : this.chain) {
            if (vertices.contains(vertex)) {
                incScore();
            }
        }
    }

    @Override
    public int compareTo(Chain<T> other) {
        return this.score > other.score? -1 : this.score < other.score? 1 : 0;
    }
}

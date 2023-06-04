package com.chill.graph;

import lombok.Data;

import java.util.List;
import java.util.LinkedList;
import java.util.Set;

@Data
public class Chain<T> implements Comparable<Chain<T>> {
    private int score;

    private int factorScore;

    private List<T> chain;

    public Chain() {
        this.score = 0;
        this.factorScore = 0;
        this.chain = new LinkedList<>();
    }

    private void incScore() {
        this.score += this.factorScore;
    }

    public void addVertex(T vertex) {
        this.chain.add(vertex);
    }

    public void computeScore(Set<T> vertices) {
        this.factorScore = this.chain.size();
        for (T vertex : this.chain) {
            if (vertices.contains(vertex)) {
                incScore();
            }
            this.factorScore -= 1;
        }
    }

    @Override
    public int compareTo(Chain<T> other) {
        return this.score > other.score? -1 : this.score < other.score? 1 : 0;
    }
}

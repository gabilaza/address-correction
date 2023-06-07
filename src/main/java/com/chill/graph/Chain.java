package com.chill.graph;

import lombok.Data;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@Data
public class Chain<T> implements Iterable<T>, Comparable<Chain<T>> {
    private int score;

    private LinkedList<T> chain;

    public Chain() {
        this.score = 0;
        this.chain = new LinkedList<>();
    }

    public void addScore(int score) {
        this.score += score;
    }

    public void addElement(T element) {
        this.chain.add(element);
    }

    public void addFirstElement(T element) {
        this.chain.addFirst(element);
    }

    public T getFirst() {
        return this.chain.getFirst();
    }

    public int size() {
        return this.chain.size();
    }

    @Override
    public Iterator<T> iterator() {
        return this.chain.iterator();
    }

    @Override
    public int compareTo(Chain<T> other) {
        return Integer.compare(other.score, this.score);
    }
}

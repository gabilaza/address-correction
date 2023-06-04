package com.chill.entity;

import com.chill.graph.Vertex;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.LinkedList;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "postalcodes")
public class PostalCode implements Vertex<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    public PostalCode(String name, City city) {
        this.name = name;
        this.city = city;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof PostalCode other)) {
            return false;
        }

        return this.name.equals(other.name);
    }

    @Override
    public String element() {
        return this.name;
    }

    @Override
    public Vertex<String> getParent() {
        return this.city;
    }

    @Override
    public Iterable<Vertex<String>> getChildren() {
        return new LinkedList<>();
    }

    @Override
    public String toString() {
        return "PostalCode(name=" + this.name + ")";
    }
}

package com.chill.entity;

import com.chill.graph.Vertex;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "cities")
public class City implements Vertex<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "state_id", nullable = false)
    private State state;

    @OneToMany(mappedBy = "city", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<PostalCode> postalCodes;

    public City(String name, State state) {
        this.name = name;
        this.state = state;

        this.postalCodes = new HashSet<>();
    }

    public void addPostalCode(PostalCode postalCode) {
        this.postalCodes.add(postalCode);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof City other)) {
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
        return this.state;
    }

    @Override
    public Iterable<Vertex<String>> getChildren() {
        return this.postalCodes.stream().map(pc -> (Vertex<String>) pc).toList();
    }

    @Override
    public String toString() {
        return "City(name=" + this.name + ")";
    }
}

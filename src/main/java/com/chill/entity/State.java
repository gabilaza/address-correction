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
@Table(name = "states")
public class State implements Vertex<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    @OneToMany(mappedBy = "state", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<City> cities;

    public State(String name, Country country) {
        this.name = name;
        this.country = country;

        this.cities = new HashSet<>();
    }

    public void addCity(City city) {
        this.cities.add(city);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof State)) {
            return false;
        }

        State other = (State) obj;
        return this.name.equals(other.name);
    }

    @Override
    public String element() {
        return this.name;
    }

    @Override
    public Vertex<String> getParent() {
        return this.country;
    }

    @Override
    public Iterable<Vertex<String>> getChildren() {
        return this.cities.stream().map(c -> (Vertex<String>)c).toList();
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        str.append("State(name=");
        str.append(this.name);
        str.append(")");

        return str.toString();
    }
}

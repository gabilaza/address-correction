package com.chill.entity;

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
@Table(name = "countries")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @OneToMany(mappedBy = "country", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<State> states;

    public Country(String name) {
        this.name = name;

        this.states = new HashSet<>();
    }

    public void addState(State state) {
        this.states.add(state);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Country)) {
            return false;
        }

        Country other = (Country) obj;
        return this.name.equals(other.name);
    }
}

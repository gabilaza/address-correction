package com.chill.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.HashSet;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "states")
public class State {
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
}

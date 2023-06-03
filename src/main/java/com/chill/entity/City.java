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
@Table(name = "cities")
public class City {
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
        if (obj == null || !(obj instanceof City)) {
            return false;
        }

        City other = (City) obj;
        return this.name.equals(other.name);
    }
}

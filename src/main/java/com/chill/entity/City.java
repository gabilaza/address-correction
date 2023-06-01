package com.chill.entity;

import jakarta.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
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
}

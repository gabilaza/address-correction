package com.chill.entity;

import jakarta.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "states")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String postalCode;

    @ManyToOne
    @JoinColumn(name = "state_id", nullable = false)
    private State state;

    public City(String name, String postalCode, State state) {
        this.name = name;
        this.postalCode = postalCode;
        this.state = state;
    }
}

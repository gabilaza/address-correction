package com.chill.entity;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "postalcodes")
public class PostalCode {
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
        if (obj == null || !(obj instanceof PostalCode)) {
            return false;
        }

        PostalCode other = (PostalCode) obj;
        return this.name.equals(other.name);
    }
}

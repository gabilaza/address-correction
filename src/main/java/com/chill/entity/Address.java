package com.chill.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private Country country;

    private State state;

    private City city;

    private PostalCode postalCode;
}

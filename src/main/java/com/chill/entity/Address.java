package com.chill.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private Country country;

    private State state;

    private City city;

    private PostalCode postalCode;
}

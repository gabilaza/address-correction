package com.chill.payload.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressResponse implements Serializable {
    private String country;

    private String state;

    private String city;

    private String postalCode;
}

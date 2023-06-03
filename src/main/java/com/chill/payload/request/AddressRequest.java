package com.chill.payload.request;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressRequest implements Serializable {
    @NotNull private String country;

    @NotNull private String state;

    @NotNull private String city;

    @NotNull private String postalCode;
}

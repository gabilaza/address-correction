package com.b2.payload.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressRequest {
    @NotNull
    private String country;

    @NotNull
    private String state;

    @NotNull
    private String city;
}

package com.b2.mapper;

import com.b2.payload.request.AddressRequest;
import com.b2.entity.Address;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public final class AddressMapper {

    public Address mapToAddress(AddressRequest addressRequest) {
        return new Address("address");
    }
}

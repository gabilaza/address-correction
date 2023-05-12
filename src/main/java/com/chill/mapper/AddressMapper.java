package com.chill.mapper;

import com.chill.entity.Address;
import com.chill.payload.request.AddressRequest;
import com.chill.payload.response.AddressResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public final class AddressMapper {

    public Address mapToAddress(AddressRequest addressRequest) {
        return new Address();
    }

    public AddressResponse mapToAddressResponse(Address address) {
        return new AddressResponse();
    }
}

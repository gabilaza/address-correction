package com.b2.exception.address;

import com.b2.exception.EntityNotFoundException;

import java.util.UUID;

public class AddressNotFoundException extends EntityNotFoundException {
    public AddressNotFoundException(UUID id) {
        super(String.format("Address Not Found: %s", id));
    }
}

package com.b2.exception.address;

import com.b2.exception.EntityExistsException;

import java.util.UUID;

public class AddressExistsException extends EntityExistsException {
    public AddressExistsException(UUID id) {
        super(String.format("Address Exists: %s", id));
    }
}

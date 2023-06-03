package com.chill.exception.postalcode;

import com.chill.exception.EntityExistsException;

public class PostalCodeExistsException extends EntityExistsException {
    public PostalCodeExistsException(Integer id) {
        super(String.format("PostalCode Exists: %s", id));
    }

    public PostalCodeExistsException(String name) {
        super(String.format("PostalCode Exists: %s", name));
    }
}

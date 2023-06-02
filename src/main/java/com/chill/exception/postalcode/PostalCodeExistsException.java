package com.chill.exception.postalcode;

import com.chill.exception.EntityExistsException;

public class PostalCodeExistsException extends EntityExistsException {
    public PostalCodeExistsException(Integer id) {
        super(String.format("PostalCode Exists: %s", id));
    }
}

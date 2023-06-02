package com.chill.exception.postalcode;

import com.chill.exception.EntityNotFoundException;

public class PostalCodeNotFoundException extends EntityNotFoundException {
    public PostalCodeNotFoundException(Integer id) {
        super(String.format("PostalCode Not Found: %s", id));
    }

    public PostalCodeNotFoundException(String name) {
        super(String.format("PostalCode Not Found: %s", name));
    }
}

package com.chill.exception.country;

import com.chill.exception.EntityExistsException;

public class CountryExistsException extends EntityExistsException {
    public CountryExistsException(Integer id) {
        super(String.format("Country Exists: %s", id));
    }
}

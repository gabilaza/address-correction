package com.chill.exception.country;

import com.chill.exception.EntityNotFoundException;

public class CountryNotFoundException extends EntityNotFoundException {
    public CountryNotFoundException(Integer id) {
        super(String.format("Country Not Found: %s", id));
    }
}

package com.chill.exception.city;

import com.chill.exception.EntityNotFoundException;

public class CityNotFoundException extends EntityNotFoundException {
    public CityNotFoundException(Integer id) {
        super(String.format("City Not Found: %s", id));
    }

    public CityNotFoundException(String name) {
        super(String.format("City Not Found: %s", name));
    }
}

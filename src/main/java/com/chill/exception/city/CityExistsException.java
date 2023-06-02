package com.chill.exception.city;

import com.chill.exception.EntityExistsException;

public class CityExistsException extends EntityExistsException {
    public CityExistsException(Integer id) {
        super(String.format("City Exists: %s", id));
    }

    public CityExistsException(String name) {
        super(String.format("City Exists: %s", name));
    }
}

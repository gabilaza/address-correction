package com.chill.exception.state;

import com.chill.exception.EntityExistsException;

public class StateExistsException extends EntityExistsException {
    public StateExistsException(Integer id) {
        super(String.format("State Exists: %s", id));
    }

    public StateExistsException(String name) {
        super(String.format("State Exists: %s", name));
    }
}

package com.chill.exception.state;

import com.chill.exception.EntityNotFoundException;

public class StateNotFoundException extends EntityNotFoundException {
    public StateNotFoundException(Integer id) {
        super(String.format("State Not Found: %s", id));
    }

    public StateNotFoundException(String name) {
        super(String.format("State Not Found: %s", name));
    }
}

package com.chill.service;

import com.chill.entity.State;
import com.chill.exception.state.*;
import com.chill.repository.StateRepository;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StateService {
    private static final Logger logs = LoggerFactory.getLogger(StateService.class);

    private final StateRepository stateRepository;

    public List<State> getAllStates() {
        logs.info("Get All States");
        return stateRepository.findAll();
    }

    public State getStateByName(String name) {
        return stateRepository.findByName(name).orElseThrow(() -> new StateNotFoundException(name));
    }

    public void createState(State state) {
        if (Boolean.TRUE.equals(existsStateById(state.getId()))) {
            throw new StateExistsException(state.getId());
        }

        stateRepository.save(state);
        logs.info("Created State id: " + state.getId());
    }

    public void updateState(State state) {
        if (Boolean.FALSE.equals(existsStateById(state.getId()))) {
            throw new StateNotFoundException(state.getId());
        }

        logs.info("Update State id: " + state.getId());
        stateRepository.save(state);
    }

    public void deleteState(State state) {
        if (Boolean.FALSE.equals(existsStateById(state.getId()))) {
            throw new StateNotFoundException(state.getId());
        }

        logs.info("Delete State id: " + state.getId());
        stateRepository.delete(state);
    }

    public Boolean existsStateById(Integer id) {
        if (id != null) {
            return stateRepository.existsById(id);
        }
        return Boolean.FALSE;
    }

    public Boolean existsStateByName(String name) {
        if (name != null) {
            return stateRepository.existsByName(name);
        }
        return Boolean.FALSE;
    }
}

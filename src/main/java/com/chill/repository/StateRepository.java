package com.chill.repository;

import com.chill.entity.State;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StateRepository extends JpaRepository<State, Integer> {
    Optional<State> findByName(String name);

    Boolean existsByName(String name);
}

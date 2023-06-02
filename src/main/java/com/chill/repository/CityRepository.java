package com.chill.repository;

import com.chill.entity.City;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CityRepository extends JpaRepository<City, Integer> {
    Optional<City> findByName(String name);

    Boolean existsByName(String name);
}

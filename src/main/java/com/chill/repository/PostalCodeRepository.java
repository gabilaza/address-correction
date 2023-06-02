package com.chill.repository;

import com.chill.entity.PostalCode;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostalCodeRepository extends JpaRepository<PostalCode, Integer> {
    Optional<PostalCode> findByName(String name);

    Boolean existsByName(String name);
}

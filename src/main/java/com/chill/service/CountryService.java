package com.chill.service;

import com.chill.entity.Country;
import com.chill.exception.country.*;
import com.chill.repository.CountryRepository;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CountryService {
    private static final Logger logs = LoggerFactory.getLogger(CountryService.class);

    private final CountryRepository countryRepository;

    public List<Country> getAllCountries() {
        logs.info("Get All Countries");
        return countryRepository.findAll();
    }

    public Country getCountryByName(String name) {
        return countryRepository
                .findByName(name)
                .orElseThrow(() -> new CountryNotFoundException(name));
    }

    public void createCountry(Country country) {
        if (Boolean.TRUE.equals(existsCountryById(country.getId()))) {
            throw new CountryExistsException(country.getId());
        }

        countryRepository.save(country);
        logs.info("Created Country id: " + country.getId());
    }

    public void updateCountry(Country country) {
        if (Boolean.FALSE.equals(existsCountryById(country.getId()))) {
            throw new CountryNotFoundException(country.getId());
        }

        logs.info("Update Country id: " + country.getId());
        countryRepository.save(country);
    }

    public void deleteCountry(Country country) {
        if (Boolean.FALSE.equals(existsCountryById(country.getId()))) {
            throw new CountryNotFoundException(country.getId());
        }

        logs.info("Delete Country id: " + country.getId());
        countryRepository.delete(country);
    }

    public Boolean existsCountryById(Integer id) {
        if (id != null) {
            return countryRepository.existsById(id);
        }
        return Boolean.FALSE;
    }

    public Boolean existsCountryByName(String name) {
        if (name != null) {
            return countryRepository.existsByName(name);
        }
        return Boolean.FALSE;
    }
}

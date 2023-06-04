package com.chill.service;

import com.chill.entity.City;
import com.chill.exception.city.*;
import com.chill.repository.CityRepository;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CityService {
    private static final Logger logs = LoggerFactory.getLogger(CityService.class);

    private final CityRepository cityRepository;

    public List<City> getAllCities() {
        logs.info("Get All Cities");
        return cityRepository.findAll();
    }

    public City getCityByName(String name) {
        return cityRepository.findByName(name).orElseThrow(() -> new CityNotFoundException(name));
    }

    public void createCity(City city) {
        if (Boolean.TRUE.equals(existsCityById(city.getId()))) {
            throw new CityExistsException(city.getId());
        }

        cityRepository.save(city);
        logs.info("Created City id: " + city.getId());
    }

    public void updateCity(City city) {
        if (Boolean.FALSE.equals(existsCityById(city.getId()))) {
            throw new CityNotFoundException(city.getId());
        }

        logs.info("Update City id: " + city.getId());
        cityRepository.save(city);
    }

    public void deleteCity(City city) {
        if (Boolean.FALSE.equals(existsCityById(city.getId()))) {
            throw new CityNotFoundException(city.getId());
        }

        logs.info("Delete City id: " + city.getId());
        cityRepository.delete(city);
    }

    public Boolean existsCityById(Integer id) {
        if (id != null) {
            return cityRepository.existsById(id);
        }

        return Boolean.FALSE;
    }

    public Boolean existsCityByName(String name) {
        if (name != null) {
            return cityRepository.existsByName(name);
        }

        return Boolean.FALSE;
    }
}

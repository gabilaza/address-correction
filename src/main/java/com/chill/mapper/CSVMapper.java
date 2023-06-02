package com.chill.mapper;

import com.chill.entity.Country;
import com.chill.entity.State;
import com.chill.entity.City;
import com.chill.entity.PostalCode;
import com.chill.service.CountryService;
import com.chill.service.StateService;
import com.chill.service.CityService;
import com.chill.service.PostalCodeService;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.LinkedList;
import java.io.Reader;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public final class CSVMapper {
    private static final Logger logs = LoggerFactory.getLogger(CSVMapper.class);

    enum AddressHeaders {
        COUNTRY, POSTAL_CODE, CITY, STATE
    }

    private final CSVFormat csvFomat = CSVFormat.DEFAULT
                    .builder()
                    .setHeader(AddressHeaders.class)
                    .setSkipHeaderRecord(true)
                    .build();

    private final CountryService countryService;

    private final StateService stateService;

    private final CityService cityService;

    private final PostalCodeService postalCodeService;

    public List<Country> mapToCountry(Reader csvReader) {
        List<Country> countries = new LinkedList<>();
        Country country = null;
        State state = null;
        City city = null;
        PostalCode postalCode = null;

        try {
            Iterable<CSVRecord> records = csvFomat.parse(csvReader);

            for(CSVRecord record : records) {
                String countryName = record.get(AddressHeaders.COUNTRY);
                String stateName = record.get(AddressHeaders.STATE);
                String cityName = record.get(AddressHeaders.CITY);
                String postalCodeStr = record.get(AddressHeaders.POSTAL_CODE);

                if(country == null || !countryName.equals(country.getName())) {
                    country = new Country(countryName);
                    countries.add(country);
                }
                if(state == null || !stateName.equals(state.getName())) {
                    state = new State(stateName, country);
                    country.addState(state);
                }
                if(city == null || !cityName.equals(city.getName())) {
                    city = new City(cityName, state);
                    state.addCity(city);
                }
                if(postalCode == null || postalCodeStr.equals(postalCode.getName())) {
                    postalCode = new PostalCode(postalCodeStr, city);
                    city.addPostalCode(postalCode);
                }
            }
        }
        catch (IOException exception) {
            logs.error(exception.getMessage());
        }

        return countries;
    }
}

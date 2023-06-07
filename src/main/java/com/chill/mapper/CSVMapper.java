package com.chill.mapper;

import com.chill.entity.City;
import com.chill.entity.Country;
import com.chill.entity.PostalCode;
import com.chill.entity.State;
import com.chill.normalize.Language;

import lombok.RequiredArgsConstructor;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public final class CSVMapper {
    private static final Logger logs = LoggerFactory.getLogger(CSVMapper.class);

    enum AddressHeaders {
        COUNTRY,
        POSTAL_CODE,
        CITY,
        STATE
    }

    /**
     * Parses a CSV file into a list of Country objects.
     * The CSV file should contain records of postal addresses,
     * where each record has columns for country, state, city, and postal code.
     * This method creates a hierarchical
     * structure, where each Country object contains State objects,
     * each State contains City objects, and each City
     * contains PostalCode objects.
     * <p>
     * During parsing, this method ensures that each country, state, city, and postal code appears only once in the
     * hierarchy.
     * If a postal code appears in different cities, or a city appears in different states, or a state
     * appears in different countries, separate objects will be created for each distinct occurrence.
     * <p>
     * If the CSV file is not correctly formatted, or an I/O error occurs during parsing, this method will log the
     * error message and return an empty list.
     *
     * @param csvReader the reader to read the CSV file from
     * @return a list of Country objects parsed from the CSV file, or an empty list if an error occurred
     */

    public List<Country> mapToCountry(Reader csvReader) {
        CSVFormat csvFormat =
                CSVFormat.DEFAULT
                        .builder()
                        .setHeader(AddressHeaders.class)
                        .setSkipHeaderRecord(true)
                        .build();
        Map<String, Country> countriesMap = new HashMap<>();
        Map<String, List<State>> statesMap = new HashMap<>();
        Map<String, List<City>> citiesMap = new HashMap<>();
        Map<String, List<PostalCode>> postalCodesMap = new HashMap<>();

        Country country;
        State state;
        City city;
        PostalCode postalCode;

        try {
            Iterable<CSVRecord> records = csvFormat.parse(csvReader);

            for (CSVRecord record : records) {
                String countryName = record.get(AddressHeaders.COUNTRY);
                String stateName = record.get(AddressHeaders.STATE);
                String cityName = record.get(AddressHeaders.CITY);
                String postalCodeName = record.get(AddressHeaders.POSTAL_CODE);

                if (!countriesMap.containsKey(countryName)) {
                    country = new Country(countryName);
                    countriesMap.put(countryName, country);
                } else {
                    country = countriesMap.get(countryName);
                }

                if (!statesMap.containsKey(stateName)) {
                    state = new State(stateName, country);
                    statesMap.put(stateName, new LinkedList<>());
                    statesMap.get(stateName).add(state);
                } else {
                    state =
                            statesMap.get(stateName).stream()
                                    .filter(s -> countryName.equals(s.getCountry().getName()))
                                    .findAny()
                                    .orElse(null);
                    if (state == null) {
                        state = new State(stateName, country);
                        statesMap.get(stateName).add(state);
                    }
                }
                country.addState(state);

                if (!citiesMap.containsKey(cityName)) {
                    city = new City(cityName, state);
                    citiesMap.put(cityName, new LinkedList<>());
                    citiesMap.get(cityName).add(city);
                } else {
                    city =
                            citiesMap.get(cityName).stream()
                                    .filter(c -> stateName.equals(c.getState().getName()))
                                    .findAny()
                                    .orElse(null);
                    if (city == null) {
                        city = new City(cityName, state);
                        citiesMap.get(cityName).add(city);
                    }
                }
                state.addCity(city);

                if (!postalCodesMap.containsKey(postalCodeName)) {
                    postalCode = new PostalCode(postalCodeName, city);
                    postalCodesMap.put(postalCodeName, new LinkedList<>());
                    postalCodesMap.get(postalCodeName).add(postalCode);
                } else {
                    postalCode =
                            postalCodesMap.get(postalCodeName).stream()
                                    .filter(pc -> cityName.equals(pc.getCity().getName()))
                                    .findAny()
                                    .orElse(null);
                    if (postalCode == null) {
                        postalCode = new PostalCode(postalCodeName, city);
                        postalCodesMap.get(postalCodeName).add(postalCode);
                    }
                }
                city.addPostalCode(postalCode);
            }
        } catch (IOException exception) {
            logs.error(exception.getMessage());
        }

        return new ArrayList<>(countriesMap.values());
    }

    /**
     * Parses a CSV file and maps each line of translations into a list of Strings, then adds each
     * list into a primary list, which is returned at the end.
     *
     * @param csvReader The reader to read the CSV data.
     * @return A list of lists of Strings. Each sublist represents a line from the CSV file, where
     *     each element is a translation of the same word into different languages. The languages
     *     are determined by the LanguageHeaders enum. If there's an IOException during processing,
     *     the method prints the stack trace and returns the list of translations parsed up to the
     *     point of the exception.
     */
    public Map<Language, List<String>> mapToTranslation(Reader csvReader) {
        CSVFormat csvFormat =
                CSVFormat.DEFAULT
                        .builder()
                        .setHeader(Language.class)
                        .setSkipHeaderRecord(true)
                        .build();

        Map<Language, List<String>> words = new HashMap<>();
        for (Language language : Language.values()) {
            words.put(language, new ArrayList<>());
        }

        try {
            Iterable<CSVRecord> records = csvFormat.parse(csvReader);

            for (CSVRecord record : records) {
                for (Language language : Language.values()) {
                    String word = record.get(language);
                    words.get(language).add(word);
                }
            }
        } catch (IOException exception) {
            logs.error(exception.getMessage());
        }

        return words;
    }
}

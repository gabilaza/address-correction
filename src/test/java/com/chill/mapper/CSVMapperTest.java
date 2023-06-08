package com.chill.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.chill.entity.City;
import com.chill.entity.Country;
import com.chill.entity.PostalCode;
import com.chill.entity.State;
import com.chill.normalize.Language;

import io.micrometer.common.lang.Nullable;
import org.junit.jupiter.api.Test;

import java.io.Reader;
import java.io.StringReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

class CSVMapperTest {
    private final CSVMapper csvMapper = new CSVMapper();

    @Test
    void givenCSVData_whenMappedToCountry_thenCorrectNumberOfCountriesStatesCitiesAndPostalCodesReturned() {
        String mockCSVData = """
                COUNTRY,POSTAL_CODE,CITY,STATE
                USA,10001,New York,New York,
                USA,10002,New York,New York""";
        Reader csvReader = new StringReader(mockCSVData);
        List<Country> countries = csvMapper.mapToCountry(csvReader);

        assertEquals(1, countries.size());
        Country country = countries.get(0);
        assertEquals("USA", country.getName());

        Set<State> states = country.getStates();
        assertEquals(1, states.size());
        State state = states.iterator().next();
        assertEquals("New York", state.getName());

        Set<City> cities = state.getCities();
        assertEquals(1, cities.size());
        City city = cities.iterator().next();
        assertEquals("New York", city.getName());

        Set<PostalCode> postalCodes = city.getPostalCodes();
        assertEquals(2, postalCodes.size());
        assertTrue(postalCodes.stream().anyMatch(pc -> "10001".equals(pc.getName())));
        assertTrue(postalCodes.stream().anyMatch(pc -> "10002".equals(pc.getName())));
    }

    @Test
    void givenEmptyCSV_whenMappedToCountry_thenNoCountriesReturned() {
        String mockCSVData = "COUNTRY,POSTAL_CODE,CITY,STATE";
        Reader csvReader = new StringReader(mockCSVData);
        List<Country> countries = csvMapper.mapToCountry(csvReader);

        assertTrue(countries.isEmpty());
    }

    @Test
    void givenCSVDataWithMultipleCountries_whenMappedToCountry_thenCorrectNumberOfCountriesReturned() {
        String mockCSVData = """
                COUNTRY,POSTAL_CODE,CITY,STATE
                USA,10001,New York,New York,
                Canada,20001,Toronto,Ontario""";
        Reader csvReader = new StringReader(mockCSVData);
        List<Country> countries = csvMapper.mapToCountry(csvReader);

        assertEquals(2, countries.size());
        assertTrue(countries.stream().anyMatch(c -> "USA".equals(c.getName())));
        assertTrue(countries.stream().anyMatch(c -> "Canada".equals(c.getName())));
    }

    @Test
    void givenCSVDataWithMultipleStates_whenMappedToCountry_thenCorrectNumberOfStatesReturned() {
        String mockCSVData = """
                COUNTRY,POSTAL_CODE,CITY,STATE
                USA,10001,New York,New York,
                USA,20001,San Francisco,California""";
        Reader csvReader = new StringReader(mockCSVData);
        List<Country> countries = csvMapper.mapToCountry(csvReader);

        assertEquals(1, countries.size());
        Country country = countries.get(0);
        assertEquals("USA", country.getName());

        Set<State> states = country.getStates();
        assertEquals(2, states.size());
        assertTrue(states.stream().anyMatch(s -> "New York".equals(s.getName())));
        assertTrue(states.stream().anyMatch(s -> "California".equals(s.getName())));
    }

    @Test
    void givenCSVDataWithMultipleCitiesInOneState_whenMappedToCountry_thenCorrectNumberOfCitiesReturned() {
        String mockCSVData = """
                COUNTRY,POSTAL_CODE,CITY,STATE
                USA,10001,New York,New York,
                USA,20001,Buffalo,New York""";
        Reader csvReader = new StringReader(mockCSVData);
        List<Country> countries = csvMapper.mapToCountry(csvReader);

        assertEquals(1, countries.size());
        Country country = countries.get(0);
        assertEquals("USA", country.getName());

        Set<State> states = country.getStates();
        assertEquals(1, states.size());
        State state = states.iterator().next();
        assertEquals("New York", state.getName());

        Set<City> cities = state.getCities();
        assertEquals(2, cities.size());
        assertTrue(cities.stream().anyMatch(c -> "New York".equals(c.getName())));
        assertTrue(cities.stream().anyMatch(c -> "Buffalo".equals(c.getName())));
    }

    @Test
    void givenCSVDataWithTranslations_whenMappedToTranslation_thenCorrectTranslationsReturned() {

        String mockCSVData = """
                ROMANIAN,ENGLISH,ITALIAN,GERMAN,FRENCH,SPANISH
                Salut,Hello,Ciao,Hallo,Bonjour,Hola
                La revedere,Goodbye,Arrivederci,Auf Wiedersehen,Au revoir,Adiós""";

        Reader csvReader = new StringReader(mockCSVData);
        Map<Language, List<String>> words = csvMapper.mapToTranslation(csvReader);

        assertEquals(6, words.size());

        assertEquals(List.of("Salut", "La revedere"), words.get(Language.ROMANIAN));
        assertEquals(List.of("Hello", "Goodbye"), words.get(Language.ENGLISH));
        assertEquals(List.of("Ciao", "Arrivederci"), words.get(Language.ITALIAN));
        assertEquals(List.of("Hallo", "Auf Wiedersehen"), words.get(Language.GERMAN));
        assertEquals(List.of("Bonjour", "Au revoir"), words.get(Language.FRENCH));
        assertEquals(List.of("Hola", "Adiós"), words.get(Language.SPANISH));
    }

    @Test
    void givenCSVWithDuplicatePostalCodes_whenMapToCountry_thenReturnsCorrectDataStructure() {
        String mockCSVData = """
                COUNTRY,POSTAL_CODE,CITY,STATE
                USA,10001,New York,New York,
                USA,10001,New York,New York""";
        Reader csvReader = new StringReader(mockCSVData);
        List<Country> countries = csvMapper.mapToCountry(csvReader);

        assertEquals(1, countries.size());
        Country country = countries.get(0);
        assertEquals("USA", country.getName());

        Set<State> states = country.getStates();
        assertEquals(1, states.size());
        State state = states.iterator().next();
        assertEquals("New York", state.getName());

        Set<City> cities = state.getCities();
        assertEquals(1, cities.size());
        City city = cities.iterator().next();
        assertEquals("New York", city.getName());

        Set<PostalCode> postalCodes = city.getPostalCodes();
        assertEquals(1, postalCodes.size());
    }

    @Test
    void givenCSVWithDuplicateCities_whenMapToCountry_thenReturnsCorrectDataStructure() {
        String mockCSVData = """
                COUNTRY,POSTAL_CODE,CITY,STATE
                USA,10001,New York,New York,
                USA,10002,New York,New York""";
        Reader csvReader = new StringReader(mockCSVData);
        List<Country> countries = csvMapper.mapToCountry(csvReader);

        assertEquals(1, countries.size());
        Country country = countries.get(0);
        assertEquals("USA", country.getName());

        Set<State> states = country.getStates();
        assertEquals(1, states.size());
        State state = states.iterator().next();
        assertEquals("New York", state.getName());

        Set<City> cities = state.getCities();
        assertEquals(1, cities.size());
        City city = cities.iterator().next();
        assertEquals("New York", city.getName());

        Set<PostalCode> postalCodes = city.getPostalCodes();
        assertEquals(2, postalCodes.size());
    }

    @Test
    void givenCSVWithDuplicateStates_whenMapToCountry_thenReturnsCorrectDataStructure() {
        String mockCSVData = """
                COUNTRY,POSTAL_CODE,CITY,STATE
                USA,10001,New York,New York,
                USA,10002,Buffalo,New York""";
        Reader csvReader = new StringReader(mockCSVData);
        List<Country> countries = csvMapper.mapToCountry(csvReader);

        assertEquals(1, countries.size());
        Country country = countries.get(0);
        assertEquals("USA", country.getName());

        Set<State> states = country.getStates();
        assertEquals(1, states.size());
        State state = states.iterator().next();
        assertEquals("New York", state.getName());

        Set<City> cities = state.getCities();
        assertEquals(2, cities.size());
    }

    @Test
    void givenCSVWithUniqueStates_whenMapToCountry_thenReturnsCorrectDataStructure() {
        String mockCSVData = """
                COUNTRY,POSTAL_CODE,CITY,STATE
                USA,10001,New York,New York,
                USA,20001,San Francisco,California""";
        Reader csvReader = new StringReader(mockCSVData);
        List<Country> countries = csvMapper.mapToCountry(csvReader);

        assertEquals(1, countries.size());
        Country country = countries.get(0);
        assertEquals("USA", country.getName());

        Set<State> states = country.getStates();
        assertEquals(2, states.size());
        assertTrue(states.stream().anyMatch(s -> "New York".equals(s.getName())));
        assertTrue(states.stream().anyMatch(s -> "California".equals(s.getName())));
    }

    @Test
    void givenCSVWithUniqueCities_whenMapToCountry_thenReturnsCorrectDataStructure() {
        String mockCSVData = """
                COUNTRY,POSTAL_CODE,CITY,STATE
                USA,10001,New York,New York,
                USA,10002,Buffalo,New York""";
        Reader csvReader = new StringReader(mockCSVData);
        List<Country> countries = csvMapper.mapToCountry(csvReader);

        assertEquals(1, countries.size());
        Country country = countries.get(0);
        assertEquals("USA", country.getName());

        Set<State> states = country.getStates();
        assertEquals(1, states.size());
        State state = states.iterator().next();
        assertEquals("New York", state.getName());

        Set<City> cities = state.getCities();
        assertEquals(2, cities.size());
        assertTrue(cities.stream().anyMatch(c -> "New York".equals(c.getName())));
        assertTrue(cities.stream().anyMatch(c -> "Buffalo".equals(c.getName())));
    }

    @Test
    void givenCSVWithUniquePostalCodes_whenMapToCountry_thenReturnsCorrectDataStructure() {
        String mockCSVData = """
                COUNTRY,POSTAL_CODE,CITY,STATE
                USA,10001,New York,New York,
                USA,10002,New York,New York""";
        Reader csvReader = new StringReader(mockCSVData);
        List<Country> countries = csvMapper.mapToCountry(csvReader);

        assertEquals(1, countries.size());
        Country country = countries.get(0);
        assertEquals("USA", country.getName());

        Set<State> states = country.getStates();
        assertEquals(1, states.size());
        State state = states.iterator().next();
        assertEquals("New York", state.getName());

        Set<City> cities = state.getCities();
        assertEquals(1, cities.size());
        City city = cities.iterator().next();
        assertEquals("New York", city.getName());

        Set<PostalCode> postalCodes = city.getPostalCodes();
        assertEquals(2, postalCodes.size());
        assertTrue(postalCodes.stream().anyMatch(pc -> "10001".equals(pc.getName())));
        assertTrue(postalCodes.stream().anyMatch(pc -> "10002".equals(pc.getName())));
    }

    @Test
    public void givenBrokenReader_whenMapToTranslation_thenReturnsEmptyMap() {
        Reader csvReader = new BrokenReader();

        Map<Language, List<String>> words = csvMapper.mapToTranslation(csvReader);

        for (Language language : Language.values()) {
            assertTrue(words.get(language).isEmpty());
        }
    }

    private static class BrokenReader extends Reader {
        @Override
        public int read(@Nullable char[] cbuf, int off, int len) throws IOException {
            throw new IOException("This is a broken reader");
        }

        @Override
        public void close() {
        }
    }
}

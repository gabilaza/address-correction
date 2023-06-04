package com.chill.config;

import com.chill.entity.Country;
import com.chill.mapper.CSVMapper;
import com.chill.service.CountryService;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Component
@RequiredArgsConstructor
@Order(1)
class AddressImporter implements ApplicationRunner {
    private static final Logger logs = LoggerFactory.getLogger(AddressImporter.class);

    private final CSVMapper csvMapper;

    private final CountryService countryService;

    @Value("${app.database.init.csv}")
    private String csvFilePath;

    @Override
    public void run(ApplicationArguments args) throws IOException {
        logs.info("Started");

        Path csvPath = Paths.get(csvFilePath);
        logs.info("Import csvPath: " + csvPath);

        try (Reader reader = Files.newBufferedReader(csvPath)) {
            List<Country> countries = csvMapper.mapToCountry(reader);

            for (Country country : countries) {
                countryService.createCountry(country);
            }
        }

        logs.info("Finished");
    }
}

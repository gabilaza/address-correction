package com.chill.config;

import com.chill.mapper.CSVMapper;
import com.chill.normalize.Language;
import com.chill.normalize.Translation;

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
import java.util.Map;

/** Application Runner class for importing translations from a CSV file into the application. */
@Component
@RequiredArgsConstructor
@Order(3)
class TranslationImporter implements ApplicationRunner {
    private static final Logger logs = LoggerFactory.getLogger(AddressImporter.class);

    private final CSVMapper csvMapper;

    private final Translation translation;

    @Value("${app.translation.init.csv}")
    private String csvFilePath;

    @Override
    public void run(ApplicationArguments args) throws IOException {
        logs.info("Started");

        Path csvPath = Paths.get(csvFilePath);
        logs.info("Import translationCSV: " + csvPath);

        try (Reader reader = Files.newBufferedReader(csvPath)) {
            Map<Language, List<String>> translationList = csvMapper.mapToTranslation(reader);
            translation.initTranslation(translationList);
        }

        logs.info("Finished");
    }
}

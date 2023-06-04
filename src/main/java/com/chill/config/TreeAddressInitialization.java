package com.chill.config;

import com.chill.graph.TreeAddress;
import com.chill.graph.Vertex;
import com.chill.service.CountryService;

import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Order(2)
class TreeAddressInitialization implements ApplicationRunner {
    private static final Logger logs = LoggerFactory.getLogger(TreeAddressInitialization.class);

    private final CountryService countryService;

    private final TreeAddress treeAddress;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        logs.info("Started");

        List<Vertex<String>> roots =
                countryService.getAllCountries().stream().map(c -> (Vertex<String>) c).toList();
        treeAddress.initTree(roots);

        logs.info("Finished");
    }
}

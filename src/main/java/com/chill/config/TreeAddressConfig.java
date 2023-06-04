package com.chill.config;

import com.chill.graph.TreeAddress;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class TreeAddressConfig {

    @Bean
    public TreeAddress treeAddress() {
        return new TreeAddress();
    }
}

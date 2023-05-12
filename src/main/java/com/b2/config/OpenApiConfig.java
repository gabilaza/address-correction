package com.b2.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(title = "CorrectAnAdress", version = "0.0.1-SNAPSHOT"),
        servers = {@Server(url = "http://localhost:8081")},
        tags = {
            @Tag(
                    name = "Address",
                    description = "These are address related requests.")
        })
public class OpenApiConfig {}

package com.example.gradle.p1;

import com.example.gradle.p1.controller.PrimesFluxHandler;
import com.example.gradle.p1.service.PrimesService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableWebFlux
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    public PrimesService primesService() {
        return new PrimesService();
    }

    @Bean
    public PrimesFluxHandler primesFluxHandler() {
        return new PrimesFluxHandler(primesService());
    }
}

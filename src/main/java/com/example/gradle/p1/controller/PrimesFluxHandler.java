package com.example.gradle.p1.controller;

import com.example.gradle.p1.exceptions.PrimeNotFoundException;
import com.example.gradle.p1.service.PrimesService;
import reactor.core.publisher.Mono;

import java.util.Optional;

public class PrimesFluxHandler {
    private final PrimesService primesService;

    public PrimesFluxHandler(final PrimesService primesService) {
        this.primesService = primesService;
    }

    Mono<Long> getClosestPrimeUnder(final Long number) {
        final Optional<Long> result = primesService.closestPrimeUnder(number);
        if (!result.isPresent()) {
            return Mono.error(new PrimeNotFoundException(number));
        }
        return Mono.just(result.get());
    }

    Mono<Boolean> isPrime(final Long number) {
        return Mono.justOrEmpty(primesService.isPrime(number));
    }
}

package com.example.gradle.p1.controller;

import com.example.gradle.p1.exceptions.PrimeNotFoundException;
import com.example.gradle.p1.service.PrimesService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.Set;

public class PrimesFluxHandler {
    private final PrimesService primesService;

    public PrimesFluxHandler(final PrimesService primesService) {
        this.primesService = primesService;
    }

    Mono<Integer> getClosestPrimeUnder(final Integer number) {
        final Optional<Integer> result = primesService.closestPrimeUnder(number);
        if (!result.isPresent()) {
            return Mono.error(new PrimeNotFoundException(number));
        }
        return Mono.just(result.get());
    }

    Mono<Boolean> isPrime(final Integer number) {
        return Mono.justOrEmpty(primesService.isPrime(number));
    }

    Flux<Integer> getPrimeNumbersUntil(final Integer number) {
        final Set<Integer> primesList = primesService.generatePrimesTo(number);
        return Flux.fromStream(primesList.stream());

    }
}

package com.example.gradle.p1.controller;

import com.example.gradle.p1.exceptions.PrimeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;


@RequestMapping(path = "/api/${api.version:-v0}")
@RestController
public class PrimesController {

    private final PrimesFluxHandler primesFluxHandler;

    @ExceptionHandler(PrimeNotFoundException.class)
    public ResponseEntity<String> handleNotFoundPrimeException(final PrimeNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @Autowired
    public PrimesController(final PrimesFluxHandler primesFluxHandler) {
        this.primesFluxHandler = primesFluxHandler;
    }

    @Validated
    @GetMapping(path = "/primesunder/{number}")
    public Mono<Integer> getClosestPrimeUnder(@PathVariable Integer number) {
        return primesFluxHandler.getClosestPrimeUnder(number);
    }

    @Validated
    @GetMapping(path = "/isprime/{number}")
    public Mono<Boolean> isPrime(@PathVariable Integer number) {
        return primesFluxHandler.isPrime(number);
    }
}

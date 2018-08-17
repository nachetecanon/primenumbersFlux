package com.example.gradle.p1.exceptions;

public class PrimeNotFoundException extends RuntimeException {

    private Long primeToSearchTo;

    public PrimeNotFoundException(final Long primeToSearchTo) {
        super("No Prime numbers found below " + primeToSearchTo);
        this.primeToSearchTo = primeToSearchTo;
    }

    public Long getPrimeToSearchTo() {
        return primeToSearchTo;
    }
}

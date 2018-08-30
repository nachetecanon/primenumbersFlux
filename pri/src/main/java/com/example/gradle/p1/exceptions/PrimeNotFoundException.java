package com.example.gradle.p1.exceptions;

public class PrimeNotFoundException extends RuntimeException {

    private final Integer primeToSearchTo;

    public PrimeNotFoundException(final Integer primeToSearchTo) {
        super("No Prime numbers found below " + primeToSearchTo);
        this.primeToSearchTo = primeToSearchTo;
    }

    public Integer getPrimeToSearchTo() {
        return primeToSearchTo;
    }
}

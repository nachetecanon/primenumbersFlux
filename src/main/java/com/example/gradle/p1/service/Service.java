package com.example.gradle.p1.service;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

public class Service {

    public static final Set<Long> WELL_KNOWN_FIRST_PRIMES = new TreeSet<>(Arrays.asList(2L, 3L, 5L, 7L, 11L, 13L, 17L, 19L, 23L, 29L, 31L, 37L, 41L, 43L, 47L, 53L, 59L, 61L, 67L, 71L, 73L, 79L, 83L, 89L, 97L, 101L, 103L, 107L, 109L, 113L, 127L, 131L, 137L, 139L, 149L, 151L, 157L, 163L, 167L, 173L, 179L, 181L, 191L, 193L, 197L, 199L));

    @Valid
    public Optional<Long> closestPrimeUnder(final @NotNull @Min(0) Long param) {
        final Long from = (param - 1) % 2 == 0 ? param - 2 : param - 1;
        for (long val = from; val > 1; val -= 2) {
            if (isPrime(val)) {
                return Optional.of(val);
            }
        }
        return Optional.empty();
    }

    @Valid
    public boolean isPrime(final @NotNull @Min(0) Long param) {
        if (param == 1L || param == 0L) {
            return false;
        }
        // first try with well known prime numbers to check if is included
        final Long max = WELL_KNOWN_FIRST_PRIMES.toArray(new Long[]{})[WELL_KNOWN_FIRST_PRIMES.size() - 1];
        if (param <= max) {
            if (WELL_KNOWN_FIRST_PRIMES.contains(param)) {
                return true;
            }
        }
        // second try to search if any of the well known primes is a divisor of param
        if (WELL_KNOWN_FIRST_PRIMES.stream().anyMatch(n -> param % n == 0)) {
            return false;
        }
        // third brute-force from last well known prime
        for (long i = max; i < param - 1; i++) {
            if (param % i == 0) {
                return false;
            }
        }

        return true;
    }
}

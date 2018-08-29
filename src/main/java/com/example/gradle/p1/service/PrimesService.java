package com.example.gradle.p1.service;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

public class PrimesService {

    public static final Set<Integer> WELL_KNOWN_FIRST_PRIMES = new TreeSet<>(Arrays.asList(2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179, 181, 191, 193, 197, 199));

    public Optional<Integer> closestPrimeUnder(final @NotNull @Min(0) Integer param) {
        final Integer from = (param - 1) % 2 == 0 ? param - 2 : param - 1;
        for (Integer val = from; val > 1; val -= 2) {
            if (isPrime(val)) {
                return Optional.of(val);
            }
        }
        return Optional.empty();
    }

    public boolean isPrime(final @NotNull @Min(0) Integer param) {
        if (param == 1 || param == 0) {
            return false;
        }
        // first try with well known prime numbers to check if is included
        final Integer max = WELL_KNOWN_FIRST_PRIMES.toArray(new Integer[]{})[WELL_KNOWN_FIRST_PRIMES.size() - 1];
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
        for (Integer i = max; i < param - 1; i++) {
            if (param % i == 0) {
                return false;
            }
        }

        return true;
    }
}

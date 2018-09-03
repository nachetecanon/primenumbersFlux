package com.example.gradle.p1.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.IntStream;

public class PrimesService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PrimesService.class);

    /**
     * searches for the highest prime number below the one passed by param
     *
     * @param param number to search the just previous prime number
     * @return optional of Integer with the number or empty
     */
    public Optional<Integer> closestPrimeUnder(final @NotNull @Min(2) Integer param) {
        final Integer from = (param - 1) % 2 == 0 ? param - 2 : param - 1;
        for (Integer val = from; val > 1; val -= 2) {
            if (isPrime(val)) {
                return Optional.of(val);
            }
        }
        return Optional.empty();
    }

    /**
     * @param param number to check
     * @return true/false
     *
     * @see "https://www.baeldung.com/java-generate-prime-numbers"
     */
    public boolean isPrime(final @NotNull @Min(2) Integer param) {
        return param >= 2 && IntStream.rangeClosed(2, (int) Math.sqrt(param))
                .filter(n -> (n & 0X1) != 0 && n >= 2)
                .allMatch(n -> param % n != 0);
    }

    public Set<Integer> generatePrimesTo(final Integer end) {
        Instant t1 = Instant.now();
        final Integer omega = Optional.ofNullable(end).orElse(2);
        Set<Integer> result = new TreeSet<>(Collections.singleton(omega));
        if (Optional.ofNullable(end).isPresent()) {
            result = sieveOfEratosthenes(end);
        }
        LOGGER.info("timing = {} msec", Duration.between(t1, Instant.now()).toMillis());
        return result;
    }


    /**
     * @param end final number to generate the list
     * @return ordered set of prime integers found
     *
     * @see "https://www.baeldung.com/java-generate-prime-numbers"
     */
    private Set<Integer> sieveOfEratosthenes(final Integer end) {
        final List<Boolean> primes = new ArrayList<>(end + 1);
        IntStream.rangeClosed(0, end + 1).boxed().forEach(index -> primes.add(Boolean.TRUE));
        for (int p = 2; p * p <= end; p++) {
            if (isPrime(p)) {
                for (int i = p * 2; i <= end; i += p) {
                    primes.set(i, Boolean.FALSE);
                }
            }
        }
        Set<Integer> primeNumbers = new TreeSet<>();
        for (int i = 2; i <= end; i++) {
            if (primes.get(i)) {
                primeNumbers.add(i);
            }
        }
        return primeNumbers;
    }


}

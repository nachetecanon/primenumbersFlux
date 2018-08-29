package com.example.gradle.p1.service;

import org.junit.Test;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class PrimesServiceTest {

    private PrimesService primesService = new PrimesService();

    @Test
    public void assert_that_is_prime_works() {
        assertThat(PrimesService.WELL_KNOWN_FIRST_PRIMES.stream().allMatch(primesService::isPrime))
                .isTrue();
    }

    @Test
    public void assert_that_0_not_prime_works() {
        assertThat(primesService.isPrime(0))
                .isFalse();
    }

    @Test
    public void assert_that_1_is_not_prime() {
        assertThat(primesService.isPrime(1))
                .isFalse();
    }

    @Test
    public void find_prime_under_some_number() {
        final List<Long> reverseList = PrimesService.WELL_KNOWN_FIRST_PRIMES.stream()
                .filter(p -> p > 2L)
                .collect(Collectors.toList());
        for (int i = reverseList.size() - 1, j = reverseList.size() - 2; j >= 0; i--, j--) {
            final @Valid Optional<Integer> primefound = primesService.closestPrimeUnder(reverseList.get(i));
            assertThat(primefound.isPresent())
                    .isTrue();
            assertThat(primefound.get()).isEqualTo(reverseList.get(j));
            System.out.println("First prime under " + reverseList.get(i) + " is " + primefound.get());
        }
    }

    @Test
    public void assert_no_prime_under_param_returns_empty() {
        assertThat(primesService.closestPrimeUnder(2))
                .isEmpty();
    }

    @Test
    public void assert_working_after_big_number_provided() {
        final Optional<Integer> result = primesService.closestPrimeUnder(7703);
        assertThat(result)
                .isNotEmpty();
        assertThat(result.get())
                /* https://www.di-mgt.com.au/primes1000.html */
                .isEqualTo(7699);
    }


}


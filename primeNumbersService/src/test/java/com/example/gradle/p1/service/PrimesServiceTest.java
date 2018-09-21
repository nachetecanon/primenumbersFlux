package com.example.gradle.p1.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.gradle.p1.Base;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;


public class PrimesServiceTest extends Base {

    private final Logger logger = LoggerFactory.getLogger(PrimesServiceTest.class);
    private PrimesService primesService = new PrimesService();

    @Test
    public void assert_that_is_prime_works() {
        assertThat(WELL_KNOWN_FIRST_PRIMES.stream().allMatch(primesService::isPrime))
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
        final List<Integer> reverseList = WELL_KNOWN_FIRST_PRIMES.stream()
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
    public void find_list_of_primes_under_number() throws Exception {
        final Set<Integer> expected = new TreeSet<>(Set.of(new ObjectMapper().readValue(new ClassPathResource("10000primes.json").getInputStream(), Integer[].class)));
        final Set<Integer> result = primesService.generatePrimesTo(expected.stream().reduce((a, b) -> b).orElse(2));

        assertThat(result)
                .isNotNull()
                .containsExactlyElementsOf(expected);
    }

    @Test
    public void find_list_of_primes_under_null_at_least_returns_the_first() throws Exception {
        final Set<Integer> expected = new TreeSet<>(Set.of(2));
        final Set<Integer> result = primesService.generatePrimesTo(null);

        assertThat(result)
                .isNotNull()
                .containsExactlyElementsOf(expected);
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


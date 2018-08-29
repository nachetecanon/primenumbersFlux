package com.example.gradle.p1.controller;


import com.example.gradle.p1.exceptions.PrimeNotFoundException;
import com.example.gradle.p1.service.PrimesService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PrimesFluxHandlerTest {

    @Autowired
    private PrimesFluxHandler primesFluxHandler;

    @MockBean
    private PrimesService primesService;

    @Test
    public void assert_that_closest_prime_under_is_properly_called() {
        when(primesService.closestPrimeUnder(anyInt())).thenReturn(Optional.of(3));
        final Mono<Integer> result = primesFluxHandler.getClosestPrimeUnder(5);
        result.subscribe(r -> {
            assertThat(r).isNotNull();
            assertThat(r).isEqualTo(3);
        });
    }

    @Test
    public void assert_that_when_closest_prime_number_not_found() {
        when(primesService.closestPrimeUnder(anyInt()))
                .thenReturn(Optional.empty());
        primesFluxHandler.getClosestPrimeUnder(2)
                .doOnError(throwable -> {
                    assertThat(throwable)
                            .isInstanceOf(PrimeNotFoundException.class);
                });
    }

    @Test
    public void assert_that_is_prime_is_correctly_called_for_true() {
        when(primesService.isPrime(anyInt())).thenReturn(Boolean.TRUE);
        final Mono<Boolean> result = primesFluxHandler.isPrime(64543);
        result.subscribe(r -> {
            assertThat(r).isNotNull();
            assertThat(r).isTrue();
        });
    }

    @Test
    public void assert_that_is_prime_is_correctly_called_for_false() {
        when(primesService.isPrime(anyInt())).thenReturn(Boolean.FALSE);
        final Mono<Boolean> result = primesFluxHandler.isPrime(64543);
        result.subscribe(r -> {
            assertThat(r).isNotNull();
            assertThat(r).isFalse();
        });
    }
}

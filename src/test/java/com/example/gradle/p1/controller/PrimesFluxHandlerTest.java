package com.example.gradle.p1.controller;


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
import static org.mockito.ArgumentMatchers.anyLong;
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
        when(primesService.closestPrimeUnder(anyLong())).thenReturn(Optional.of(3L));
        final Mono<Long> result = primesFluxHandler.getClosestPrimeUnder(5L);
        result.subscribe(r -> {
            assertThat(r).isNotNull();
            assertThat(r).isEqualTo(3L);
        });
    }


    @Test
    public void assert_that_is_prime_is_correctly_called_for_true() {
        when(primesService.isPrime(anyLong())).thenReturn(Boolean.TRUE);
        final Mono<Boolean> result = primesFluxHandler.isPrime(64543L);
        result.subscribe(r -> {
            assertThat(r).isNotNull();
            assertThat(r).isTrue();
        });
    }

    @Test
    public void assert_that_is_prime_is_correctly_called_for_false() {
        when(primesService.isPrime(anyLong())).thenReturn(Boolean.FALSE);
        final Mono<Boolean> result = primesFluxHandler.isPrime(64543L);
        result.subscribe(r -> {
            assertThat(r).isNotNull();
            assertThat(r).isFalse();
        });
    }
}

package com.example.gradle.p1.controller;

import com.example.gradle.p1.exceptions.PrimeNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.when;

@RunWith(SpringRunner.class)
@WebFluxTest
public class PrimesControllerTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private PrimesFluxHandler primesFluxHandler;

    @Test
    public void testGetClosestPrimeSuccess() {
        when(primesFluxHandler.getClosestPrimeUnder(anyLong()))
                .thenReturn(Mono.just(193L));
        webClient.get().uri("/primesunder/{id}", 123L)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Long.class)
                .isEqualTo(193L);
    }

    @Test
    public void testGetClosestPrimeFail() {
        when(primesFluxHandler.getClosestPrimeUnder(anyLong()))
                .thenThrow(new PrimeNotFoundException(2L));
        webClient.get().uri("/primesunder/{id}", 2L)
                .exchange()
                .expectStatus()
                .isNotFound()
                .expectBody(String.class);

    }
}

package com.example.gradle.p1.controller;

import com.example.gradle.p1.Base;
import com.example.gradle.p1.exceptions.PrimeNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.when;

@RunWith(SpringRunner.class)
@WebFluxTest
public class PrimesControllerTest extends Base {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private PrimesFluxHandler primesFluxHandler;

    @Value("${api.version:-v0}")
    private String version;
    private String apiBaseUri;

    @Before
    public void setup() {
        apiBaseUri = "/api/" + version;
    }

    @Test
    public void testGetClosestPrimeSuccess() {
        when(primesFluxHandler.getClosestPrimeUnder(anyInt()))
                .thenReturn(Mono.just(193));
        webClient.get().uri(apiBaseUri + "/primesunder/{id}", 123)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Integer.class)
                .isEqualTo(193);
    }

    @Test
    public void testGetClosestPrimeFail() {
        when(primesFluxHandler.getClosestPrimeUnder(anyInt()))
                .thenThrow(new PrimeNotFoundException(2));
        webClient.get().uri(apiBaseUri + "/primesunder/{id}", 2)
                .exchange()
                .expectStatus()
                .isNotFound()
                .expectBody(String.class);

    }

    @Test
    public void testIsPrimeOk() {
        when(primesFluxHandler.isPrime(anyInt()))
                .thenReturn(Mono.just(true));
        webClient.get().uri(apiBaseUri + "/isprime/{number}", 1)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Boolean.class)
                .isEqualTo(Boolean.TRUE);

    }

    @Test
    public void testIsPrimeBad() {
        when(primesFluxHandler.isPrime(anyInt()))
                .thenReturn(Mono.just(false));
        webClient.get().uri(apiBaseUri + "/isprime/{number}", 1)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Boolean.class)
                .isEqualTo(Boolean.FALSE);

    }
}

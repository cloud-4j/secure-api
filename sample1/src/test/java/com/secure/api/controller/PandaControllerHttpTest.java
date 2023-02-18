package com.secure.api.controller;

import com.secure.api.dto.Panda;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class PandaControllerHttpTest {

    @Value(value="${local.server.port}")
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void getPandas() {
        ResponseEntity<Panda[]> result =
                restTemplate.getForEntity("http://localhost:" + port + "/pandas", Panda[].class);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
//        Objects.requireNonNull(result.getBody());
//        assertThat(result.getBody()).hasSize(2);
    }
}
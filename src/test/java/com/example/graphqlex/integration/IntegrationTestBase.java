package com.example.graphqlex.integration;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.graphql.test.tester.HttpGraphQlTester;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTestBase {

    @LocalServerPort
    protected int port;

    protected WebTestClient webTestClient;

    protected HttpGraphQlTester graphQlTester;


    @BeforeEach
    public void setup(){
        this.webTestClient = WebTestClient
                .bindToServer()
                .baseUrl("http://localhost:"+port+"/graphql")
                .build();
        this.graphQlTester = HttpGraphQlTester.create(webTestClient);
    }
}

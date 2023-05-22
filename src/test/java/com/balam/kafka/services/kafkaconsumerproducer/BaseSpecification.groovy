package com.balam.kafka.services.kafkaconsumerproducer

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.servlet.client.MockMvcWebTestClient
import org.springframework.web.context.WebApplicationContext
import spock.lang.Specification

@SpringBootTest
@ActiveProfiles("local")
@TestPropertySource("classpath:db-unit-test.properties")
class BaseSpecification extends Specification{

    @Autowired
    WebApplicationContext context

    WebTestClient webTestClient

    def setup() {
        webTestClient = MockMvcWebTestClient.bindToApplicationContext(context).build()
    }
}

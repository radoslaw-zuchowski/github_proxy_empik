package com.zuchol.githubproxy

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.tomakehurst.wiremock.WireMockServer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock
import org.springframework.test.context.ContextConfiguration
import spock.lang.Shared
import spock.lang.Specification

import static com.github.tomakehurst.wiremock.client.WireMock.configureFor

@AutoConfigureWireMock(port = 0)
@ContextConfiguration(initializers = [PostgresqlInitializer.class] )
@SpringBootTest(classes = [GithubProxyApplication],
        properties = "spring.profiles.active=integration",
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BaseSpecification extends Specification {

    @LocalServerPort
    protected int port

    @Autowired
    protected TestRestTemplate restTemplate

    protected def mapper = new ObjectMapper()

    @Shared
    private def wireMockServer = new WireMockServer()

    def setup() {
        wireMockServer.start()
        configureFor("localhost", 8080)
    }

    def cleanup() {
        wireMockServer.stop()
    }
}
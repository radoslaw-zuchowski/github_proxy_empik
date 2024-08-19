package com.zuchol.githubproxy.stubs

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse
import static com.github.tomakehurst.wiremock.client.WireMock.get
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching

class GithubStub {
    static def githubResponse200(String login) {
        stubFor(get(urlPathMatching("/users/${login}"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody("""
                    {
                        "id": "12345", 
                        "login": "${login}", 
                        "name": "Test User", 
                        "type": "User", 
                        "avatar_url": "http://example.com/avatar.png", 
                        "created_at": "2023-01-01T00:00:00Z", 
                        "followers": 5, 
                        "public_repos": 10
                    }
                """)
            )
        )
    }

    static def githubResponseNotFound(String login) {
        stubFor(get(urlPathMatching("/users/${login}"))
                .willReturn(aResponse()
                        .withStatus(404)
                        .withHeader("Content-Type", "application/json")
                )
        )
    }

    static def githubResponseServerError(String login) {
        stubFor(get(urlPathMatching("/users/${login}"))
                .willReturn(aResponse()
                        .withStatus(500)
                        .withHeader("Content-Type", "application/json")
                )
        )
    }
}

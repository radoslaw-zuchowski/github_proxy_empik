package com.zuchol.githubproxy

import com.zuchol.githubproxy.adapters.incoming.api.dto.UserDto

import static com.zuchol.githubproxy.stubs.GithubStub.githubResponse200
import static com.zuchol.githubproxy.stubs.GithubStub.githubResponseNotFound
import static com.zuchol.githubproxy.stubs.GithubStub.githubResponseServerError

class GetGithubLoginSpecification extends BaseSpecification {

    def "should return user data for existing user"() {
        given:
        String login = "testuser"
        String url = "/users/${login}"
        githubResponse200(login)

        when:
        def response = restTemplate.getForEntity(url, UserDto)

        then:
        response.statusCode.value() == 200

        and:
        response.body.login() == login
    }

    def "should return 404 for non-existent user"() {
        given:
        String login = "nonexistent"
        String url = "/users/${login}"
        githubResponseNotFound(login)

        when:
        def response = restTemplate.getForEntity(url, String)

        then:
        response.statusCode.value() == 404

        and:
        def expectedError = [
                statusCode: 404,
                userMessage: "The login was not found in the system",
                errorDetails: "User nonexistent not found"
        ]

        def actualError = mapper.readValue(response.body, Map.class)
        actualError.statusCode == expectedError.statusCode
        actualError.userMessage == expectedError.userMessage
        actualError.errorDetails == expectedError.errorDetails
    }

    def "should return 500 for server error"() {
        given:
        String login = "testuser2"
        String url = "/users/${login}"
        githubResponseServerError(login)

        when:
        def response = restTemplate.getForEntity(url, String)

        then:
        response.statusCode.value() == 500

        and:
        def expectedError = [
                statusCode: 500,
                userMessage: "Something went wrong",
        ]

        def actualError = mapper.readValue(response.body, Map.class)
        actualError.statusCode == expectedError.statusCode
        actualError.userMessage == expectedError.userMessage
    }
}
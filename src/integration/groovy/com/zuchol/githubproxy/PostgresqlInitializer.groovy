package com.zuchol.githubproxy

import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.test.context.support.TestPropertySourceUtils
import org.testcontainers.containers.PostgreSQLContainer

class PostgresqlInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static final PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:latest")
            .withDatabaseName("integration-tests-db")
            .withUsername("sa")
            .withPassword("sa")

    @Override
    void initialize(ConfigurableApplicationContext applicationContext) {
        postgreSQLContainer.start()

        def jdbcUrl = postgreSQLContainer.getJdbcUrl()
        def username = postgreSQLContainer.getUsername()
        def password = postgreSQLContainer.getPassword()

        TestPropertySourceUtils.addInlinedPropertiesToEnvironment(applicationContext,
                "spring.datasource.url=$jdbcUrl",
                "spring.datasource.username=$username",
                "spring.datasource.password=$password",
                "spring.datasource.driver-class-name=org.postgresql.Driver",
                "spring.jpa.hibernate.ddl-auto=create",
                "hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect"
        )
    }
}

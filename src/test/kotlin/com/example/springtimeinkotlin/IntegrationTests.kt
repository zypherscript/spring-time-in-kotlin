package com.example.springtimeinkotlin

import org.springframework.boot.fromApplication
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.context.annotation.Bean
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName

@TestConfiguration(proxyBeanMethods = false)
class IntegrationTests {

    @Bean
    @ServiceConnection
    fun postgresContainer(): PostgreSQLContainer<*> {
        return PostgreSQLContainer(DockerImageName.parse("postgres:latest"))
            .withDatabaseName("db")
            .withUsername("sa")
            .withPassword("password")
            .withInitScript("schema.sql")
    }
}

fun main(args: Array<String>) {
    fromApplication<SpringTimeInKotlinApplication>().with(IntegrationTests::class.java)
        .run(*args)
}
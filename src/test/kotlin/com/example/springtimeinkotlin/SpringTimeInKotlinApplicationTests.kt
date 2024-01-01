package com.example.springtimeinkotlin

import com.example.springtimeinkotlin.kx.uuid
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.boot.test.web.client.getForObject
import org.springframework.boot.test.web.client.postForObject
import org.springframework.http.HttpStatus
import kotlin.random.Random

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = [
        "spring.datasource.url=jdbc:h2:mem:testdb"
    ]
)
class SpringTimeInKotlinApplicationTests(@Autowired val client: TestRestTemplate) {

    @Test
    fun `testing if we can post and retrieve the data`() {
        val id = "${Random.nextInt()}".uuid()
        val message = Message(id, "some message")
        client.postForObject<Message>("/", message)

        val entity = client.getForEntity<String>("/$id")
        assertEquals(HttpStatus.OK, entity.statusCode)
        assertThat(entity.body).contains(message.id)
        assertThat(entity.body).contains(message.text)

        val msg = client.getForObject<Message>("/$id")!!
        assertEquals(message.id, msg.id)
        assertEquals(message.text, msg.text)
    }

    @Test
    fun `testing message not found`() {
        val id = "${Random.nextInt()}".uuid()
        val entity = client.getForEntity<String>("/$id")
        assertEquals(HttpStatus.NOT_FOUND, entity.statusCode)
    }
}

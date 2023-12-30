package com.example.springtimeinkotlin

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.GenericGenerator
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.*

@SpringBootApplication
class SpringTimeInKotlinApplication

fun main(args: Array<String>) {
    runApplication<SpringTimeInKotlinApplication>(*args)
}

@RestController
class MessageResource(val messageService: MessageService) {
    @GetMapping
    fun index(): List<Message> = messageService.findMessage()

    @PostMapping
    fun post(@RequestBody message: Message) {
        messageService.post(message)
    }
}

@Service
class MessageService(val messageRepository: MessageRepository) {

    fun findMessage(): List<Message> = messageRepository.findMessage()

    fun post(message: Message) {
        println(message)
        messageRepository.save(message)
    }
}

@Repository
interface MessageRepository : CrudRepository<Message, String> {

    @Query("select * from messages", nativeQuery = true)
    fun findMessage(): List<Message>
}

@Entity
@Table(name = "messages")
data class Message(
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    val id: UUID? = null,

    val text: String
) {
    constructor() : this(UUID.randomUUID(), "")
}
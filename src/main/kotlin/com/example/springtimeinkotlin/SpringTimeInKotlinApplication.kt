package com.example.springtimeinkotlin

import com.example.springtimeinkotlin.kx.uuid
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.http.HttpStatus
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@SpringBootApplication
class SpringTimeInKotlinApplication

fun main(args: Array<String>) {
    runApplication<SpringTimeInKotlinApplication>(*args)
}

@RestController
class MessageResource(val messageService: MessageService) {
    @GetMapping
    fun index(): List<Message> = messageService.findMessages()

    @GetMapping("/{id}")
    fun index(@PathVariable id: String): Message =
        messageService.findMessageById(id) ?: throw ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "Message not found"
        )

    @PostMapping
    fun post(@RequestBody message: Message) {
        messageService.post(message)
    }
}

@Service
class MessageService(val db: JdbcTemplate) {

    fun findMessages(): List<Message> =
        //trailing lambda use
        db.query("select * from messages") { rs, _ ->
            Message(rs.getString("id"), rs.getString("text"))
        }

    fun findMessageById(id: String): Message? =
        db.query("select * from messages where id = ?", { rs, _ ->
            Message(rs.getString("id"), rs.getString("text"))
        }, id).firstOrNull()

    fun post(message: Message) {
        db.update(
            "insert into messages values ( ?, ?)",
            message.id ?: message.text.uuid(), //extension function use
            message.text
        )
    }
}

data class Message(val id: String?, val text: String)
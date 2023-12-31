package com.example.springtimeinkotlin.kx

import java.util.*

//fun main() {
//    //trailing lambda
//    applyAction("hello", "test") { s: String -> println(s.uuid()) }
//}

fun applyAction(vararg s: String, action: (String) -> Unit) {
    s.forEach(action)
}

//extension function
fun String.uuid(): String = UUID.nameUUIDFromBytes(this.encodeToByteArray()).toString()

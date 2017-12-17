package com.ninja.todokt.backend

import com.hexagonkt.server.jetty.serve
import com.hexagonkt.store.MongoIdRepository
import java.time.LocalDateTime

// TODO Add 'context' @home @office and 'tags', change priority for enum
data class Task(
    val code: String,
    val name: String,
    val priority: Int,
    val createdAt: LocalDateTime,
    val completedAt: LocalDateTime,
    val deletedAt: LocalDateTime
)

val store = MongoIdRepository(Task::class, Task::code)

fun main(vararg args: String) {
    serve {
        assets("public")

        get { redirect("/index.html") }
    }
}

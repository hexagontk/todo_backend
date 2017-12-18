package com.hexagonkt.todokt.backend

import com.hexagonkt.rest.crud
import com.hexagonkt.server.jetty.serve
import com.hexagonkt.store.MongoIdRepository
import java.time.LocalDateTime

// TODO Add 'context' @home @office and 'tags', change priority for enum
data class Task(
    val code: String,
    val name: String,
    val priority: Int = 1,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val completedAt: LocalDateTime? = null,
    val deletedAt: LocalDateTime? = null
)

val store = MongoIdRepository(Task::class, Task::code)

fun main(vararg args: String) {
    serve {
        assets("public")

        path("/hi") {
            get { "We salute you" }
        }

        path(crud(store, "tasks"))

        get { redirect("/index.html") }
    }
}

package com.hexagonkt.todokt.backend

import com.hexagonkt.helpers.CodedException
import com.hexagonkt.http.server.Call
import com.hexagonkt.http.server.Server
import com.hexagonkt.http.server.jetty.JettyServletAdapter
import com.hexagonkt.serialization.Json
import com.hexagonkt.settings.SettingsManager.requireSetting
import com.hexagonkt.store.mongodb.MongoDbStore
import com.hexagonkt.todokt.backend.entities.Task
import java.util.*
import kotlin.text.Charsets.UTF_8

/** Store for tasks. */
val store = MongoDbStore(Task::class, Task::id, requireSetting("mongoDbUrl") as String)

fun main(vararg args: String) {
    val server = Server(JettyServletAdapter()) {

        cors()

        options("/*") { }

        path("/tasks") {

            get {
                val tasks = store.findAll()

                val taskResponse = tasks.map {
                    TaskRetrievalResponse(
                        url = it.url,
                        title = it.title,
                        order = it.order,
                        completed = it.completed
                    )
                }

                ok(taskResponse, Json, UTF_8)
            }

            get("/{id}") {
                val id = request.pathParameters["id"]

                getTask(id)

            }

            post {
                val taskCreationRequest = request.body<TaskCreationRequest>()
                val task = Task(
                    id = generateId(),
                    title = taskCreationRequest.title,
                    order = taskCreationRequest.order
                )

                store.saveOne(task)

                getTask(task.id)
            }

            patch("/{id}") {
                val id = request.pathParameters["id"]
                val taskUpdateRequest = request.body<TaskUpdateRequest>()

                store.findOne(id) ?: halt(404, "Task with id $id not found")

                val updates = mapOf(
                    Task::title.name to taskUpdateRequest.title,
                    Task::order.name to taskUpdateRequest.order,
                    Task::completed.name to taskUpdateRequest.completed
                )

                if (store.updateOne(id, updates)) getTask(id)
                else halt(400, "Unable to update task with id $id")
            }

            delete {
                store.drop()
                ok()
            }

            delete("/{id}") {
                val id = request.pathParameters["id"]

                store.findOne(id) ?: halt(404, "Task with id $id not found")

                if (store.deleteOne(id)) ok()
                else halt(400, "Unable to delete task with id $id")
            }
        }
    }

    server.start()
}

internal fun generateId(): String {
    return UUID.randomUUID().toString()
}

internal fun Call.getTask(id: String) {
    val task = store.findOne(id) ?: halt(404, "Task with id $id not found")

    val taskResponse = TaskRetrievalResponse(
            url         = task.url,
            title       = task.title,
            order       = task.order,
            completed   = task.completed
    )

    ok(taskResponse, Json, UTF_8)
}
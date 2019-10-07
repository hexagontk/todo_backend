package com.hexagonkt.todokt.backend

import com.hexagonkt.http.server.Call
import com.hexagonkt.http.server.Router
import com.hexagonkt.serialization.Json
import com.hexagonkt.todokt.backend.entities.Task
import com.hexagonkt.todokt.backend.stores.TaskStore
import java.util.*
import kotlin.text.Charsets.UTF_8


internal val router = Router {

    val store = injector.inject<TaskStore>(Task::class)

    cors()

    // Needed to allow for OPTION requests for all endpoints
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

            getTask(id, store)
        }

        post {
            val taskCreationRequest = request.body<TaskCreationRequest>()
            val task = Task(
                id = generateId(),
                title = taskCreationRequest.title,
                order = taskCreationRequest.order
            )

            store.insertOne(task)

            getTask(task.id, store)
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

            if (store.updateOne(id, updates)) getTask(id, store)
            else halt(400, "Unable to update task with id $id")
        }

        delete {
            store.deleteAll()
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

internal fun generateId(): String {
    return UUID.randomUUID().toString()
}

internal fun Call.getTask(id: String, store: TaskStore) {
    val task = store.findOne(id) ?: halt(404, "Task with id $id not found")

    val taskResponse = TaskRetrievalResponse(
        url         = task.url,
        title       = task.title,
        order       = task.order,
        completed   = task.completed
    )

    ok(taskResponse, Json, UTF_8)
}

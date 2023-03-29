package com.hexagonkt.todokt.backend

import com.hexagonkt.converters.convert
import com.hexagonkt.core.media.APPLICATION_JSON
import com.hexagonkt.http.model.ContentType
import com.hexagonkt.http.server.handlers.HttpServerContext
import com.hexagonkt.http.server.handlers.path
import com.hexagonkt.serialization.jackson.json.Json
import com.hexagonkt.serialization.parseMap
import com.hexagonkt.serialization.serialize
import com.hexagonkt.todokt.backend.entities.Task
import com.hexagonkt.todokt.backend.stores.TaskStore
import java.util.UUID


class Router(private val store: TaskStore) {
    private val tasksHandler = path("/tasks") {
        get {
            val taskResponse =
                store.findAll().map { task -> task.convert(TaskRetrievalResponse::class) }
            ok(taskResponse)
        }

        get("/{id}") {
            val id = pathParameters.getValue("id")
            getTask(id, store)
        }

        post {
            val taskCreationRequest =
                request.bodyString().parseMap(Json).convert(TaskCreationRequest::class)
            val task = Task(
                id = UUID.randomUUID().toString(),
                title = taskCreationRequest.title,
                order = taskCreationRequest.order
            )
            store.insertOne(task)
            getTask(task.id, store)
        }

        patch("/{id}") {
            val id = pathParameters.getValue("id")
            val taskUpdateRequest =
                request.bodyString().parseMap(Json).convert(TaskUpdateRequest::class)
            store.findOne(id) ?: notFound("Task with id $id not found")
            val updates = mapOf(
                Task::title.name to taskUpdateRequest.title,
                Task::order.name to taskUpdateRequest.order,
                Task::completed.name to taskUpdateRequest.completed
            )
            if (store.updateOne(id, updates)) getTask(id, store)
            else badRequest("Unable to update task with id $id")
        }

        delete {
            store.deleteAll()
            ok()
        }

        delete("/{id}") {
            val id = pathParameters.getValue("id")
            store.findOne(id) ?: notFound("Task with id $id not found")
            if (store.deleteOne(id)) ok()
            else badRequest("Unable to delete task with id $id")
        }
    }

    val handler = path {
        cors()
        after("*") {
            send(
                body = response.body.serialize(Json),
                contentType = ContentType(APPLICATION_JSON)
            )
        }
        use(tasksHandler)
    }
}

internal fun HttpServerContext.getTask(id: String, store: TaskStore): HttpServerContext {
    val task = store.findOne(id)?.let { task -> task.convert(TaskRetrievalResponse::class) }
    return if (task != null) {
        ok(task)
    } else {
        notFound("Task with id $id not found")
    }
}
